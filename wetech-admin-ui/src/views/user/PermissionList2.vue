<template>
  <a-card :bordered="false">
    <a-row :gutter="24">
      <a-col :md="6">
        <a-card>
          <a-tree
            @select="onSelect"
            :treeData="treeData "
            showIcon>
            <a-icon type="down" slot="switcherIcon"/>
            <template slot="icon" slot-scope="{value}">
              <a-icon :type="value.icon"/>
            </template>
            <template slot="title" slot-scope="{value}">
              {{ value.name }}
              <a-dropdown :trigger="['click']">
                <a class="ant-dropdown-link" href="#">
                  <a-icon type="setting" style="color:#595959;"/>
                </a>
                <a-menu slot="overlay">
                  <a-menu-item v-if="value.type==1" key="0">
                    <a href="http://www.alipay.com/">添加下级节点</a>
                  </a-menu-item>
                  <a-menu-item key="1">
                    <a href="http://www.taobao.com/">添加同级节点</a>
                  </a-menu-item>
                  <a-menu-divider/>
                  <a-menu-item key="3">删除节点</a-menu-item>
                </a-menu>
              </a-dropdown>
            </template>
          </a-tree>
        </a-card>
      </a-col>
      <a-col :md="18">
        <a-card>
          <a-form @submit="handleSubmit" :form="form">
            <a-form-item
              label="上级节点"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
              <a-input
                v-decorator="['parentName']"
                :disabled="true"
                placeholder="上级节点不存在"/>
            </a-form-item>
            <a-form-item
              label="权限名称"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
              <a-input
                v-decorator="[
                  'name',
                  {rules: [{ required: true, message: '权限名称不能为空' }]}
                ]"
                placeholder="权限的名称"/>
            </a-form-item>
            <a-form-item
              label="权限类型"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
              <a-select
                v-decorator="[
                  'type',
                  {rules: [{ required: true, message: '权限类型不能为空' }]}
                ]"
                placeholder="请选择">
                <a-select-option :value="1">菜单</a-select-option>
                <a-select-option :value="2">按钮</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item
              v-if="formData.type==1"
              label="菜单URL"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
              <a-input
                v-decorator="[
                  'url',
                  {rules: [{ required: true, message: '菜单URL不能为空' }]}
                ]"
                placeholder="请输入菜单URL"/>
            </a-form-item>
            <a-form-item
              label="权限字符串"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
              <a-input
                v-decorator="[
                  'permission',
                  {rules: [{ required: true, message: '请输入权限字符串' }]}
                ]"
                placeholder="权限字符串最好是唯一键"/>
            </a-form-item>
            <a-form-item
              label="图标"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
              <a-input
                @change="onChange"
                v-decorator="[
                  'icon',
                  {rules: [{ required: true, message: '请输入图标' }]}
                ]"
                placeholder="菜单/按钮的图标">
                <a-icon slot="addonAfter" :type="formData.icon" />
              </a-input>
            </a-form-item>
            <a-form-item
              label="排序"
              :labelCol="{lg: {span: 7}, sm: {span: 7}}"
              :wrapperCol="{lg: {span: 10}, sm: {span: 17} }"
              :required="false"
            >
              <a-input-number
                v-decorator="[
                  'sort',
                  {rules: [{ required: true, message: '请输入排序' }]}
                ]"
                :min="0"
                :max="100"/>
            </a-form-item>
            <a-form-item
              :wrapperCol="{ span: 24 }"
              style="text-align: center"
            >
              <a-button htmlType="submit" type="primary">提交</a-button>
              <a-button style="margin-left: 8px">保存</a-button>
            </a-form-item>
          </a-form>
        </a-card>
      </a-col>
    </a-row>
  </a-card>
</template>

<script>
import {getPermissionTree} from '@/api/manage'
import {Tree} from 'ant-design-vue'
import pick from 'lodash.pick'

export default {
  name: 'TreeList',
  components: {
    'a-tree': Tree
  },
  data () {
    return {
      treeData: [],
      formData: {},
      // form
      form: this.$form.createForm(this)
    }
  },
  created () {
    getPermissionTree().then(res => {
      this.treeData = this.formatTree(res.result)
    })
  },
  methods: {
    onSelect (selectedKeys, info) {
      console.log('selected value', info)
      this.formData = { ...info.node.value, parentName: info.node.$parent.value ? info.node.$parent.value.name : '根节点' }
      this.$nextTick(() => {
        if (this.formData.type === 1) {
          this.form.setFieldsValue(pick(this.formData, 'parentName', 'name', 'type', 'url', 'permission', 'icon', 'sort'))
        } else {
          this.form.setFieldsValue(pick(this.formData, 'parentName', 'name', 'type', 'permission', 'icon', 'sort'))
        }
      })
    },
    onChange (e) {
      this.formData.icon = e.target.value
    },
    formatTree (permissionTree) {
      const permissions = []
      permissionTree.forEach(tree => {
        permissions.push({
          scopedSlots: {
            title: 'title',
            icon: 'icon'
          },
          key: tree.id,
          children: tree.children.length > 0 ? this.formatTree(tree.children) : null,
          value: tree
        })
      })
      return permissions
    },
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          // eslint-disable-next-line no-console
          console.log('Received values of form: ', values)
        }
      })
    }
  }
}
</script>
