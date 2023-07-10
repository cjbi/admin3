<template>
  <div>
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" :icon="Plus" @click="addVisible = true;Object.assign(form, new StorageImpl());"
                   v-action:storage:create>
          新增对象存储
        </el-button>
      </div>
      <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="name" label="名称" width="120"></el-table-column>
        <el-table-column prop="storageId" label="标识" align="center"></el-table-column>
        <el-table-column prop="type" label="类型" width="120">
          <template #default="{ row }">
            <el-tag class="ml-2" type="success">{{ row.type }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="bucketName" label="Bucket名称"></el-table-column>
        <el-table-column prop="endpoint" label="Endpoint"></el-table-column>
        <el-table-column prop="address" label="访问地址"></el-table-column>
        <el-table-column prop="storagePath" label="存储目录"></el-table-column>
        <el-table-column prop="isDefault" label="默认对象存储">
          <template #default="{ row }">
            <el-tag effect="dark" link v-if="row.isDefault" v-action:storage:markAsDefault>默认使用</el-tag>
            <el-popconfirm title="设为默认?" v-else @confirm="handleMarkAsDefaultConfig(row)">
              <template #reference>
                <el-button link>设为默认</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
        <el-table-column prop="createUser" label="创建人"></el-table-column>
        <el-table-column prop="createTime" label="创建时间"></el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button-group>
              <el-button text :icon="Edit" @click="handleEdit(scope.row)" v-action:storage:update>
                编辑
              </el-button>
              <el-button text :icon="Delete" class="red" @click="handleDelete(scope.row)" v-action:storage:delete>
                删除
              </el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 新增弹出框 -->
    <el-dialog title="新增" v-model="addVisible" width="35%">
      <el-form label-width="100px">
        <el-form-item label="名称">
          <el-input v-model="form.name"/>
        </el-form-item>
        <el-form-item label="类型">
          <el-radio-group v-model="form.type" size="small" @change="handleTypeChange">
            <el-radio-button label="OSS">阿里云 OSS</el-radio-button>
            <el-radio-button label="OBS">华为云 OBS</el-radio-button>
            <el-radio-button label="S3">其他 S3 协议</el-radio-button>
            <el-radio-button label="LOCAL">本地</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-alert title="以下所有输入都支持环境变量，使用语法为 {{VARIABLE_NAME}}" show-icon center :closable="false" style="margin-bottom: 18px"/>
        <el-form-item label="Endpoint" v-if="form.type!=='LOCAL'">
          <el-input v-model="form.endpoint" placeholder="对象存储资源的Endpoint"/>
        </el-form-item>
        <el-form-item label="Bucket 名称" v-if="form.type!=='LOCAL'">
          <el-input v-model="form.bucketName" placeholder="对象存储资源的桶名称"/>
        </el-form-item>
        <el-form-item label="AccessKey" v-if="form.type!=='LOCAL'">
          <el-input v-model="form.accessKey"/>
        </el-form-item>
        <el-form-item label="SecretKey" v-if="form.type!=='LOCAL'">
          <el-input v-model="form.secretKey"/>
        </el-form-item>
        <el-form-item label="访问地址">
          <el-input v-model="form.address" placeholder="不填写，则使用厂商地址（本地存储除外）"/>
        </el-form-item>
        <el-form-item label="存储目录">
          <el-input v-model="form.storagePath" placeholder="不填写，默认存储到 files 目录下"/>
        </el-form-item>
      </el-form>
      <template #footer>
				<span class="dialog-footer">
					<el-button @click="addVisible = false">取 消</el-button>
					<el-button type="primary" @click="handleCreateConfig">确 定</el-button>
				</span>
      </template>
    </el-dialog>

    <!-- 修改弹出框 -->
    <el-dialog title="新增" v-model="editVisible" width="35%">
      <el-form label-width="100px">
        <el-form-item label="名称">
          <el-input v-model="form.name"/>
        </el-form-item>
        <el-form-item label="类型">
          <el-radio-group v-model="form.type" size="small" @change="handleTypeChange">
            <el-radio-button label="OSS">阿里云 OSS</el-radio-button>
            <el-radio-button label="OBS">华为云 OBS</el-radio-button>
            <el-radio-button label="S3">其他 S3 协议</el-radio-button>
            <el-radio-button label="LOCAL">本地</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-alert title="以下所有输入都支持环境变量，使用语法为 {{VARIABLE_NAME}}" show-icon center :closable="false" style="margin-bottom: 18px"/>
        <el-form-item label="Endpoint" v-if="form.type!=='LOCAL'">
          <el-input v-model="form.endpoint" placeholder="对象存储资源的Endpoint"/>
        </el-form-item>
        <el-form-item label="Bucket 名称" v-if="form.type!=='LOCAL'">
          <el-input v-model="form.bucketName" placeholder="对象存储资源的桶名称"/>
        </el-form-item>
        <el-form-item label="AccessKey" v-if="form.type!=='LOCAL'">
          <el-input v-model="form.accessKey"/>
        </el-form-item>
        <el-form-item label="SecretKey" v-if="form.type!=='LOCAL'">
          <el-input v-model="form.secretKey"/>
        </el-form-item>
        <el-form-item label="访问地址">
          <el-input v-model="form.address"/>
        </el-form-item>
        <el-form-item label="存储目录">
          <el-input v-model="form.storagePath" placeholder="不填写，默认存储到 files 目录下"/>
        </el-form-item>
      </el-form>
      <template #footer>
				<span class="dialog-footer">
					<el-button @click="editVisible = false">取 消</el-button>
					<el-button type="primary" @click="handleUpdateConfig">确 定</el-button>
				</span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup lang="ts">
import {reactive, ref} from 'vue';
import {Delete, Edit, Plus} from '@element-plus/icons-vue';
import {
  createStorageConfig,
  deleteStorageConfig,
  getStorageConfigList,
  markAsDefaultConfig,
  updateStorageConfig
} from "../api/storage";
import {ElMessage, ElMessageBox} from "element-plus";

interface Storage {
  id?: number | null;
  storageId?: string | null;
  name: string;
  type: string;
  endpoint?: string;
  accessKey?: string;
  secretKey?: string;
  bucketName?: string;
  isDefault?: boolean;
  address?: string;
  storagePath?: string;
  createUser?: string;
  createTime?: string;
}

class StorageImpl implements Storage {
  id = null;
  endpoint = 'oss-cn-shanghai.aliyuncs.com';
  name = '';
  type = 'OSS';
  storagePath = 'files';
  accessKey = '';
  secretKey = '';
  bucketName = '';
  isDefault = false;
  address = '';
  createUser = '';
  createTime = '';
}

const addVisible = ref<boolean>(false);
const editVisible = ref<boolean>(false);

let form = reactive<Storage>(new StorageImpl());

const tableData = ref<Storage[]>([]);

// 获取表格数据
const getData = () => {
  getStorageConfigList()
    .then(res => {
      tableData.value = res.data;
    });
};
getData();

const handleEdit = (record: any) => {
  form.id = record.id;
  form.endpoint = record.endpoint;
  form.name = record.name;
  form.type = record.type;
  form.storagePath = record.storagePath;
  form.accessKey = record.accessKey;
  form.secretKey = record.secretKey;
  form.bucketName = record.bucketName;
  form.isDefault = record.isDefault;
  form.address = record.address;

  editVisible.value = true;
}

const handleDelete = (record: any) => {
  ElMessageBox.confirm('确定要删除吗？', '提示', {
    type: 'warning'
  }).then(() => {
    deleteStorageConfig(record.id).then(res => {
      getData();
      ElMessage.success('删除成功');
    });
  }).catch(() => {
  });

}


const handleTypeChange = () => {
  switch (form.type) {
    case 'OSS':
      Object.assign(form, {endpoint: 'oss-cn-shanghai.aliyuncs.com'});
      break;
    case 'OBS':
      Object.assign(form, {endpoint: 'obs.cn-north-4.myhuaweicloud.com'});
      break;
    default:
      Object.assign(form, {endpoint: ''})
  }
}

const handleCreateConfig = () => {
  createStorageConfig(form).then(res => {
    getData();
    ElMessage.success('新增成功');
    addVisible.value = false;
  })
}

const handleUpdateConfig = () => {
  updateStorageConfig(form.id || -1, form).then(res => {
    getData();
    ElMessage.success('修改成功');
    editVisible.value = false;
  });
}

const handleMarkAsDefaultConfig = (record: any) => {
  markAsDefaultConfig(record.id).then(res => {
    getData();
  })
}


</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.table {
  width: 100%;
  font-size: 14px;
}

.mr10 {
  margin-right: 10px;
}
</style>
