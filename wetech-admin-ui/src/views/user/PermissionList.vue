<template>
  <a-card :bordered="false">
    <s-table
      rowKey="id"
      :columns="columns"
      :data="loadData"
      :rowSelection="rowSelection"
      showPagination="auto">
      <span slot="icon" slot-scope="text">
        <a-icon v-if="text!=null" :type="text" /> {{ text }}
      </span>
      <span slot="action" slot-scope="text, record">
        <template>
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多 <a-icon type="down"/>
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a href="javascript:;" @click="handleLocked(record)">添加同级节点</a>
              </a-menu-item>
              <a-menu-item>
                <a href="javascript:;">添加下级节点</a>
              </a-menu-item>
              <a-menu-item>
                <a href="javascript:;">删除节点</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </template>
      </span>
    </s-table>
    <!--<create-form ref="createModal" @ok="handleOk"/>-->
  </a-card>
</template>
<script>
import { Ellipsis, STable } from '@/components'
import { getPermissions } from '@/api/manage'

export default {
  name: 'PermissionList',
  components: { Ellipsis, STable },
  data () {
    return {
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
    }
  }
}
</script>
