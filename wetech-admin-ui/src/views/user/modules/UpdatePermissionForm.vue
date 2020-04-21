<template>
  <a-modal
    title="更新权限"
    :width="640"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
  >
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item v-show="false" label="编号">
          <a-input
            v-decorator="[ 'id']"
            placeholder="隐藏的编号"/>
        </a-form-item>
        <a-form-item
          label="权限名称"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol">
          <a-input
            v-decorator="[
              'name',
              {rules: [{ required: true, message: '权限名称不能为空' }]}
            ]"
            placeholder="权限的名称"/>
        </a-form-item>
        <a-form-item
          label="权限类型"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol">
          <a-select
            @change="onChangeType"
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
          label="权限字符串"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol">
          <a-input
            v-decorator="[
              'permission',
              {rules: [{ required: true, message: '请输入权限字符串' }]}
            ]"
            placeholder="权限字符串最好是唯一键"/>
        </a-form-item>
        <a-form-item
          label="图标"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol">
          <a-input
            @change="onChangeIcon"
            @click="iconSelectorVisible = true"
            v-decorator="[
              'icon',
              {rules: [{ message: '请输入图标' }]}
            ]"
            placeholder="菜单/按钮的图标">
            <a-icon slot="addonAfter" :type="icon" />
          </a-input>
          <a-modal v-model="iconSelectorVisible" :footer="null">
            <p>选择图标</p>
            <icon-selector v-model="icon" @change="handleIconChange"/>
          </a-modal>
        </a-form-item>
        <a-form-item
          label="排序"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          :required="false"
        >
          <a-input-number
            v-decorator="[
              'sort',{initialValue: 0}
            ]"/>
        </a-form-item>
        <a-form-item
          v-show="type==1"
          label="动态路由配置"
          :labelCol="labelCol"
          :wrapperCol="wrapperCol">
          <a-textarea
            :rows="4"
            placeholder="动态路由配置"
            v-decorator="[
              'config'
            ]"
          />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { updatePermission } from '@/api/manage'
import pick from 'lodash.pick'
import IconSelector from '@/components/IconSelector'

export default {
  components: {
    IconSelector
  },
  data () {
    return {
      labelCol: {
        xs: { span: 7 },
        sm: { span: 7 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 13 }
      },
      visible: false,
      iconSelectorVisible: false,
      confirmLoading: false,

      form: this.$form.createForm(this),
      icon: '',
      type: 1
    }
  },
  created () {
  },
  methods: {
    edit (record) {
      this.visible = true
      this.$nextTick(() => {
        console.log(pick(record, 'id', 'name', 'type', 'permission', 'url', 'icon', 'sort', 'config'))
        this.form.setFieldsValue(pick(record, 'id', 'name', 'type', 'permission', 'url', 'icon', 'sort', 'config'))
        this.icon = record.icon
        this.type = record.type
      })
    },
    onChangeType (item) {
      this.type = item
    },
    onChangeIcon (e) {
      this.icon = e.target.value
    },
    handleSubmit () {
      const { form: { validateFields } } = this
      this.confirmLoading = true
      validateFields((errors, values) => {
        if (!errors) {
          updatePermission(values).then((res) => {
            this.confirmLoading = false
            if (res.success) {
              this.visible = false
              this.form.resetFields()
              this.$emit('ok', values)
            } else {
              this.$message.warning(res.message)
            }
          })
        } else {
          this.confirmLoading = false
        }
      })
    },
    handleCancel () {
      this.visible = false
    },
    handleIconChange (icon) {
      this.icon = icon
      this.form.setFieldsValue({ 'icon': icon })
      this.iconSelectorVisible = false
    }
  }
}
</script>
