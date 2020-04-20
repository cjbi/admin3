<template>
  <a-card :bordered="false" :style="{ height: '100%' }">
    <a-row :gutter="24">
      <a-col :md="4">
        <a-list itemLayout="horizontal" :dataSource="roles">
          <a-list-item slot="renderItem" slot-scope="item, index" :key="index">
            <a-list-item-meta :style="{ marginBottom: '0',display:'flex' }">
              <span slot="description" style="text-align: left; display: block">{{ item.description }}</span>
              <a slot="title" style="text-align: left; display: block; font-size: 16px;" @click="selected(item)">{{
                item.name }}</a>
            </a-list-item-meta>
            <a-popconfirm v-action:role:delete title="确定删除角色？" @confirm="del(item)">
              <a :style="{ display: 'flex'}">
                <a-icon type="delete"/>
              </a>
            </a-popconfirm>
          </a-list-item>
        </a-list>
        <span v-action:role:create :style="{margin: '25px',fontSize: '15px',display:'block'}">
          <a @click="add()"><a-icon type="plus"/> 新增角色</a>
        </span>
      </a-col>
      <a-col :md="20">
        <div style="max-width: 1000px;padding-top: 100px;" v-if="!mdl.role">
          <a-empty/>
        </div>
        <div style="max-width: 1000px" v-if="mdl.role">
          <a-divider v-if="isMobile()"/>
          <div>
            <h3>角色：{{ mdl.name }}</h3>
          </div>
          <a-form :form="form" :layout="isMobile() ? 'vertical' : 'horizontal'">
            <a-form-item v-show="false" label="编号">
              <a-input
                v-decorator="[ 'id']"
                placeholder="隐藏的编号"/>
            </a-form-item>
            <a-form-item label="唯一标识">
              <a-input
                v-decorator="[ 'role', {rules: [{ required: true, message: 'Please input unique key!' }]} ]"
                placeholder="请填写唯一键"/>
            </a-form-item>

            <a-form-item label="角色名称">
              <a-input
                v-decorator="[ 'name', {rules: [{ required: true, message: 'Please input role name!' }]} ]"
                placeholder="请填写角色名称"/>
            </a-form-item>

            <a-form-item label="状态">
              <a-select v-decorator="[ 'status', {rules: []} ]">
                <a-select-option :value="1">正常</a-select-option>
                <a-select-option :value="2">禁用</a-select-option>
              </a-select>
            </a-form-item>

            <a-form-item label="备注说明">
              <a-textarea
                :row="3"
                v-decorator="[ 'description', {rules: [{ required: true, message: '请填写!' }]} ]"
                placeholder="请填写角色名称"/>
            </a-form-item>

            <a-form-item label="拥有权限">
              <div v-for="(menuPermission, index) in permissions" :key="index">
                <a-row>
                  <a-col :span="18" :style="{fontWeight:'bold'}">{{ menuPermission.name }}</a-col>
                  <a-col :span="6" :style="{textAlign:'right'}">
                    <a-switch
                      size="small"
                      v-model="menuPermission.checked"
                      @change="onChangeSwitch($event,menuPermission)"/>
                  </a-col>
                </a-row>
                <a-divider type="horizontal" :style="{margin: '0px'}"/>
                <a-row :gutter="16" v-for="(checkboxPermission, index2) in menuPermission.children" :key="index2">
                  <a-col :xl="4" :lg="24">
                    {{ checkboxPermission.name }}：
                  </a-col>
                  <a-col :xl="20" :lg="24">
                    <a-checkbox
                      v-if="checkboxPermission.actionsOptions.length > 0"
                      :indeterminate="checkboxPermission.indeterminate"
                      :checked="checkboxPermission.checkedAll"
                      @change="onChangeCheckAll($event, checkboxPermission)">
                      全选
                    </a-checkbox>
                    <a-checkbox-group
                      :options="checkboxPermission.actionsOptions"
                      v-model="checkboxPermission.selected"
                      @change="onChangeCheck(checkboxPermission)"/>
                  </a-col>
                </a-row>
              </div>
            </a-form-item>
            <a-form-item>
              <a-button type="primary" @click="save" :loading="loading" v-action:role:update>保存</a-button>
            </a-form-item>
          </a-form>
        </div>
      </a-col>
    </a-row>
  </a-card>
</template>

<script>
import { deleteRole, saveRole, getPermissionTree, getRoleList } from '@/api/manage'
import { mixinDevice } from '@/utils/mixin'
import pick from 'lodash.pick'
import { message, Empty } from 'ant-design-vue'

export default {
  name: 'RoleList',
  mixins: [mixinDevice],
  components: { 'a-empty': Empty },
  data () {
    return {
      form: this.$form.createForm(this),
      mdl: {},
      roles: [],
      permissions: [],
      loading: false
    }
  },
  created () {
    getRoleList().then((res) => {
      this.roles = res.result
    })
    this.loadpermissions()
  },
  methods: {
    callback (val) {
      console.log(val)
    },
    del (item) {
      this.roles = this.roles.filter(role => role.role !== item.role)
      if (item.id) {
        deleteRole(item.id).then(r => {
          if (r.success) {
            getRoleList().then((res) => {
              this.roles = res.result
            })
          } else {
            message.warning(r.message)
          }
        })
      }
    },
    add () {
      const time = new Date().getTime()
      this.roles.push({
        id: null,
        role: 'new_role_' + time,
        name: '新增角色',
        description: '新增的一个角色',
        status: 2,
        permissionIds: []
      })
    },
    save () {
      const permissionIds = []
      // 选中的元素
      this.permissions.forEach(menuPermission => {
        if (menuPermission.checked) {
          permissionIds.push(menuPermission.id)
        }
        menuPermission.children.forEach(checkboxPermission => checkboxPermission.selected.forEach(id => permissionIds.push(id)))
      })
      this.form.validateFields((err, values) => {
        if (!err) {
          values.permissionIds = permissionIds.join(',')
          this.loading = true
          saveRole(values).then(r => {
            if (r.success) {
              this.loading = false
              getRoleList().then((res) => {
                message.info(res.message)
                getRoleList().then((res) => {
                  this.roles = res.result
                })
              })
            } else {
              this.loading = false
              message.warning(r.message)
            }
          })
        }
      })
    },
    selected (record) {
      this.mdl = Object.assign({}, record)
      // 有权限表，处理勾选
      if (this.mdl.permissionIds && this.permissions) {
        // 先处理要勾选的权限结构
        this.permissions.forEach(menuPermission => {
          const checked = this.mdl.permissionIds.indexOf(menuPermission.id) > -1
          this.onChangeSwitch(checked, menuPermission)
          menuPermission.children.forEach(checkboxPermission => {
            if (checkboxPermission.actionsOptions.length <= 0) {
              return
            }
            const selected = checkboxPermission.actionsOptions.map(entity => entity.value).filter(permissionId => this.mdl.permissionIds.indexOf(permissionId) > -1)
            checkboxPermission.selected = selected || []
            this.onChangeCheck(checkboxPermission)
          })
        })
      }

      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.mdl, 'id', 'role', 'name', 'status', 'description'))
      })
    },
    onChangeCheck (permission) {
      permission.indeterminate = !!permission.selected.length && (permission.selected.length < permission.actionsOptions.length)
      permission.checkedAll = permission.selected.length === permission.actionsOptions.length
    },
    onChangeSwitch (checked, permission) {
      permission.checked = checked
    },
    onChangeCheckAll (e, permission) {
      Object.assign(permission, {
        selected: e.target.checked ? permission.actionsOptions.map(obj => obj.value) : [],
        indeterminate: false,
        checkedAll: e.target.checked
      })
    },
    loadpermissions () {
      getPermissionTree().then(res => {
        const permissions = res.result
        permissions.forEach(menuPermission => {
          // switch切换开关
          menuPermission.checked = false
          // checkbox组
          menuPermission.children.forEach(checkboxPermission => {
            // 全选
            checkboxPermission.checkedAll = false
            // 是否不确定的
            checkboxPermission.indeterminate = false
            // 要选中的项
            checkboxPermission.selected = []
            // 多选项
            checkboxPermission.actionsOptions = checkboxPermission.children ? checkboxPermission.children.map(option => {
              return {
                label: option.name,
                value: option.id
              }
            }) : []
          })
        })
        this.permissions = permissions
      })
    }
  }
}
</script>

<style scoped>

</style>
