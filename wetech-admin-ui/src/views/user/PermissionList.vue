<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh(true)">刷新</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <s-table
      rowKey="id"
      ref="table"
      :columns="columns"
      :data="loadData"
      :rowSelection="rowSelection"
      showPagination="auto">
      <span slot="icon" slot-scope="text">
        <a-icon v-if="text!=null" :type="text"/> {{ text }}
      </span>
      <span slot="action" slot-scope="text, record">
        <template>
          <a @click="handleEdit(record)" v-action:permission:update>编辑</a>
          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多 <a-icon type="down"/>
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a href="javascript:;" @click="handleAdd(record)" v-action:permission:create>添加同级节点</a>
              </a-menu-item>
              <a-menu-item>
                <a href="javascript:;" @click="handleAdd(record,true)" v-action:permission:create>添加下级节点</a>
              </a-menu-item>
              <a-menu-item v-show="record.children===null">
                <a href="javascript:;" @click="handleDel(record)" v-action:permission:delete>删除节点</a>
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
import CreateForm from './modules/CreatePermissionForm'
import UpdateForm from './modules/UpdatePermissionForm'
import { Ellipsis, STable } from '@/components'
import { getPermissions, deletePermission } from '@/api/manage'

export default {
  name: 'PermissionList',
  components: { CreateForm, UpdateForm, Ellipsis, STable },
  data () {
    return {
      // 表头
      columns: [
        {
          title: '权限名称',
          dataIndex: 'name'
        },
        {
          title: '权限类型',
          dataIndex: 'typeName'
        },
        {
          title: '权限字符串',
          dataIndex: 'permission'
        },
        {
          title: '图标',
          dataIndex: 'icon',
          scopedSlots: { customRender: 'icon' }
        },
        {
          title: '排序',
          dataIndex: 'sort'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: '150px',
          scopedSlots: { customRender: 'action' }
        }
      ],
      rowSelection: {
        onChange: (selectedRowKeys, selectedRows) => {
          console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows)
        },
        onSelect: (record, selected, selectedRows) => {
          console.log(record, selected, selectedRows)
        },
        onSelectAll: (selected, selectedRows, changeRows) => {
          console.log(selected, selectedRows, changeRows)
        }
      },
      loadData: parameter => {
        return getPermissions().then(res => {
          return res.result
        })
      }
    }
  },
  methods: {
    handleOk () {
      this.$refs.table.refresh()
    },
    handleEdit (record) {
      this.$refs.updateModal.edit(record)
    },
    handleAdd (record, isChildNode = false) {
      this.$refs.createModal.add(record, isChildNode)
    },
    handleDel (record) {
      deletePermission(record.id).then(res => {
        if (res.success) {
          this.$refs.table.refresh()
        } else {
          this.$message.warning(res.message)
        }
      })
    }
  }
}
</script>
