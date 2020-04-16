<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="24">
            <a-form-item label="用户编号">
              <a-input v-model="queryParam.id" placeholder=""/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="是否锁定">
              <a-select v-model="queryParam.locked" placeholder="请选择" default-value="0">
                <a-select-option value="">全部</a-select-option>
                <a-select-option value="1">禁用</a-select-option>
                <a-select-option value="0">启用</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <template v-if="advanced">
            <a-col :md="8" :sm="24">
              <a-form-item label="用户名">
                <a-input v-model="queryParam.username" placeholder=""/>
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="!advanced && 8 || 24" :sm="24">
            <span
              class="table-page-search-submitButtons"
              :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
              <a @click="toggleAdvanced" style="margin-left: 8px">
                {{ advanced ? '收起' : '展开' }}
                <a-icon :type="advanced ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <div class="table-operator">
      <a-button type="primary" v-action:user:create icon="plus" @click="$refs.createModal.add()">新建</a-button>
      <!--a-dropdown去除v-action:edit，暂时不加权限 -->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item v-action:user:delete key="1" @click="handleBatchDelete">
            <a-icon type="delete"/>
            删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px">
          批量操作
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
    </div>

    <s-table
      ref="table"
      size="default"
      rowKey="id"
      :columns="columns"
      :data="loadData"
      :alert="options.alert"
      :rowSelection="options.rowSelection"
      showPagination="auto"
    >
      <span slot="locked" slot-scope="text">
        <a-badge :status="text | lockedTypeFilter" :text="text | lockedFilter"/>
      </span>
      <span slot="roleNames" slot-scope="roleNames">
        <a-tag v-for="roleName in roleNames" :key="roleName">{{ roleName }}</a-tag>
      </span>
      <span slot="action" slot-scope="text, record">
        <template>
          <a v-action:user:update @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多 <a-icon type="down"/>
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a href="javascript:;" @click="handleLocked(record)">
                  {{ record.locked?'启用':'禁用' }}
                </a>
              </a-menu-item>
              <a-menu-item>
                <a href="javascript:;" v-action:user:delete @click="handleDelete(record)">删除</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </template>
      </span>
    </s-table>
    <create-form ref="createModal" @ok="handleOk"/>
    <update-form ref="updateModal" @ok="handleOk"/>
  </a-card>
</template>

<script>
import { Ellipsis, STable } from '@/components'
import CreateForm from './modules/CreateUserForm'
import UpdateForm from './modules/UpdateUserForm'
import { deleteUser, getUserList, lockUser } from '@/api/manage'
import { message } from 'ant-design-vue'

const lockedMap = {
  1: {
    status: 'error',
    text: '禁用'
  },
  0: {
    status: 'success',
    text: '启用'
  }
}

export default {
  name: 'UserList',
  components: {
    CreateForm,
    UpdateForm,
    STable,
    Ellipsis
  },
  data () {
    return {
      mdl: {},
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      columns: [
        {
          title: '用户编号',
          dataIndex: 'id',
          sorter: true
        },
        {
          title: '用户名',
          dataIndex: 'username',
          sorter: true
        }, {
          title: '拥有的角色列表',
          dataIndex: 'roleNames',
          scopedSlots: { customRender: 'roleNames' }
        },
        {
          title: '是否锁定',
          dataIndex: 'locked',
          scopedSlots: { customRender: 'locked' }
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '150px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        console.log('loadData.parameter', parameter)
        return getUserList(Object.assign(parameter, this.queryParam))
          .then(res => {
            return res.result
          })
      },
      selectedRowKeys: [],
      selectedRows: [],

      // custom table alert & rowSelection
      options: {
        alert: {
          show: true,
          clear: () => {
            this.selectedRowKeys = []
          }
        },
        rowSelection: {
          selectedRowKeys: this.selectedRowKeys,
          onChange: this.onSelectChange
        }
      }
    }
  },
  filters: {
    lockedFilter (type) {
      return lockedMap[type].text
    },
    lockedTypeFilter (type) {
      return lockedMap[type].status
    }
  },
  created () {
    this.tableOption()
  },
  methods: {
    tableOption () {
      if (!this.optionAlertShow) {
        this.options = {
          alert: {
            show: true,
            clear: () => {
              this.selectedRowKeys = []
            }
          },
          rowSelection: {
            selectedRowKeys: this.selectedRowKeys,
            onChange: this.onSelectChange,
            getCheckboxProps: record => ({
              props: {
                disabled: record.no === 'No 2', // Column configuration not to be checked
                name: record.no
              }
            })
          }
        }
        this.optionAlertShow = true
      } else {
        this.options = {
          alert: false,
          rowSelection: null
        }
        this.optionAlertShow = false
      }
    },
    handleEdit (record) {
      console.log(record)
      this.$refs.updateModal.edit(record)
    },
    handleDelete (record) {
      deleteUser({ ids: [record.id] }).then(r => {
        if (r.success) {
          this.$refs.table.refresh()
        } else {
          message.warning(r.message)
        }
      })
    },
    handleBatchDelete () {
      deleteUser({ ids: this.selectedRowKeys }).then(r => {
        if (r.success) {
          this.$refs.table.refresh()
          this.selectedRowKeys = []
        } else {
          message.warning(r.message)
        }
      })
    },
    handleOk () {
      this.$refs.table.refresh()
    },
    handleLocked (record) {
      lockUser({ id: record.id, locked: record.locked ? 0 : 1 })
        .then(r => {
          if (r.success) {
            this.$refs.table.refresh()
          } else {
            message.warning(r.message)
          }
        })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    resetSearchForm () {
    }
  }
}
</script>
