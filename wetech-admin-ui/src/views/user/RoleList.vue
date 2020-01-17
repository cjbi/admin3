<template>
  <a-card :bordered="false" :style="{ height: '100%' }">
    <a-row :gutter="24">
      <a-col :md="4">
        <a-list itemLayout="horizontal" :dataSource="roles">
          <a-list-item slot="renderItem" slot-scope="item, index" :key="index">
            <a-icon type="unlock"/>
            <a-divider type="vertical"/>
            <a-popconfirm title="确定删除角色？" @confirm="del(item.id)">
              <a :style="{ display: 'flex'}">
                <a-icon type="delete"/>
              </a>
            </a-popconfirm>
            <a-list-item-meta :style="{ marginBottom: '0',display:'flex' }">
              <span slot="description" style="text-align: left; display: block">{{ item.description }}</span>
              <a slot="title" style="text-align: left; display: block" @click="edit2(item)">{{ item.name }}</a>
            </a-list-item-meta>
          </a-list-item>
        </a-list>
        <span :style="{margin: '25px',fontSize: '15px',display:'block'}">
          <a @click="add()"><a-icon type="plus"/> 新增角色</a>
        </span>
      </a-col>
      <a-col :md="20">
        <div style="max-width: 800px">
          <a-divider v-if="isMobile()"/>
          <div v-if="mdl.id">
            <h3>角色：{{ mdl.name }}</h3>
          </div>
          <a-form :form="form" :layout="isMobile() ? 'vertical' : 'horizontal'">
            <a-form-item label="唯一标识">
              <a-input v-decorator="[ 'role', {rules: [{ required: true, message: 'Please input unique key!' }]} ]" placeholder="请填写唯一键"/>
            </a-form-item>

            <a-form-item label="角色名称">
              <a-input v-decorator="[ 'name', {rules: [{ required: true, message: 'Please input role name!' }]} ]" placeholder="请填写角色名称"/>
            </a-form-item>

            <a-form-item label="状态">
              <a-select v-decorator="[ 'status', {rules: []} ]">
                <a-select-option :value="1">正常</a-select-option>
                <a-select-option :value="2">禁用</a-select-option>
              </a-select>
            </a-form-item>

            <a-form-item label="备注说明">
              <a-textarea :row="3" v-decorator="[ 'description', {rules: [{ required: true, message: 'Please input role name!' }]} ]" placeholder="请填写角色名称"/>
            </a-form-item>

            <a-form-item label="拥有权限">
              <div v-for="(menuPermission, index) in permissions2" :key="index">
                <a-row>
                  <a-col :span="18" :style="{fontWeight:'bold'}">{{ menuPermission.name }}</a-col>
                  <a-col :span="6" :style="{textAlign:'right'}">
                    <a-switch size="small" v-model="menuPermission.checked" @change="onChangeSwitch($event,menuPermission)"/>
                  </a-col>
                </a-row>
                <a-divider type="horizontal" :style="{margin: '0px'}"/>
                <a-row :gutter="16" v-for="(buttonPermission, index) in menuPermission.children" :key="index">
                  <a-col :xl="4" :lg="24">
                    {{ buttonPermission.name }}：
                  </a-col>
                  <a-col :xl="20" :lg="24">
                    <a-checkbox
                      v-if="buttonPermission.actionsOptions.length > 0"
                      :indeterminate="buttonPermission.indeterminate"
                      :checked="buttonPermission.checkedAll"
                      @change="onChangeCheckAll($event, buttonPermission)">
                      全选
                    </a-checkbox>
                    <a-checkbox-group :options="buttonPermission.actionsOptions" v-model="buttonPermission.selected" @change="onChangeCheck(buttonPermission)"/>
                  </a-col>
                </a-row>
              </div>
            </a-form-item>
            <!--<a-form-item label="拥有权限">
              <a-row>
                <a-col :span="18" :style="{fontWeight:'bold'}">用户管理</a-col>
                <a-col :span="6" :style="{textAlign:'right'}">
                  <a-switch checkedChildren="显示" unCheckedChildren="隐藏"/>
                </a-col>
              </a-row>
              <a-divider type="horizontal" :style="{margin: '0px'}"/>
              <a-row :gutter="16" v-for="(permission, index) in permissions" :key="index">
                <a-col :xl="4" :lg="24">
                  {{ permission.name }}：
                </a-col>
                <a-col :xl="20" :lg="24">
                  <a-checkbox
                    v-if="permission.actionsOptions.length > 0"
                    :indeterminate="permission.indeterminate"
                    :checked="permission.checkedAll"
                    @change="onChangeCheckAll($event, permission)">
                    全选
                  </a-checkbox>
                  <a-checkbox-group :options="permission.actionsOptions" v-model="permission.selected" @change="onChangeCheck(permission)"/>
                </a-col>
              </a-row>
            </a-form-item>-->
            <a-form-item>
              <a-button type="primary">保存</a-button>
            </a-form-item>
          </a-form>
        </div>
      </a-col>
    </a-row>
  </a-card>
</template>

<script>
import { getPermissions, getPermissionTree, getRoleList } from '@/api/manage'
import { mixinDevice } from '@/utils/mixin'
import { actionToObject } from '@/utils/permissions'
import pick from 'lodash.pick'

export default {
  name: 'RoleList',
  mixins: [mixinDevice],
  components: {},
  data () {
    return {
      form: this.$form.createForm(this),
      mdl: {},
      roles: [],
      permissions: [],
      permissions2: []
    }
  },
  created () {
    getRoleList().then((res) => {
      this.roles = res.result
      // console.log('this.roles', this.roles)
    })
    this.loadPermissions2()
    this.loadPermissions()
  },
  methods: {
    callback (val) {
      console.log(val)
    },
    del (id) {
      console.log(id)
    },
    add () {
      this.roles.push({
        name: '新增角色',
        description: '新增一个角色'
      })
      // this.edit({ id: 0 })
    },
    edit2 (record) {
      this.mdl = Object.assign({}, record)
      // 有权限表，处理勾选
      if (this.mdl.permissionIds && this.permissions2) {
        // 先处理要勾选的权限结构
        this.permissions2.forEach(menuPermission => {
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
          console.log(menuPermission.children)
        })
      }

      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.mdl, 'role', 'name', 'status', 'description'))
      })
    },
    edit (record) {
      this.mdl = Object.assign({}, record)
      // 有权限表，处理勾选
      if (this.mdl.permissions && this.permissions) {
        // 先处理要勾选的权限结构
        const permissionsAction = {}
        this.mdl.permissions.forEach(permission => {
          permissionsAction[permission.permissionId] = permission.actionEntitySet.map(entity => entity.action)
        })

        console.log('permissionsAction', permissionsAction)
        // 把权限表遍历一遍，设定要勾选的权限 action
        this.permissions.forEach(permission => {
          const selected = permissionsAction[permission.id]
          permission.selected = selected || []
          this.onChangeCheck(permission)
        })

        console.log('this.permissions', this.permissions)
      }

      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.mdl, 'role', 'name', 'status', 'description'))
      })
      console.log('this.mdl', this.mdl)
    },

    onChangeCheck (permission) {
      permission.indeterminate = !!permission.selected.length && (permission.selected.length < permission.actionsOptions.length)
      permission.checkedAll = permission.selected.length === permission.actionsOptions.length
    },
    onChangeSwitch (checked, permission) {
      console.log(`a-switch to ${checked}`)
      permission.checked = checked
    },
    onChangeCheckAll (e, permission) {
      console.log('permission:', permission)

      Object.assign(permission, {
        selected: e.target.checked ? permission.actionsOptions.map(obj => obj.value) : [],
        indeterminate: false,
        checkedAll: e.target.checked
      })
    },
    loadPermissions2 () {
      getPermissionTree().then(res => {
        this.permissions2 = res.result
      })
    },
    loadPermissions () {
      getPermissions().then(res => {
        const result = res.result
        this.permissions = result.map(permission => {
          const options = actionToObject(permission.actionData)
          permission.checkedAll = false
          permission.selected = []
          permission.indeterminate = false
          permission.actionsOptions = options.map(option => {
            return {
              label: option.describe,
              value: option.action
            }
          })
          return permission
        })
      })
    }
  }
}
</script>

<style scoped>

</style>
