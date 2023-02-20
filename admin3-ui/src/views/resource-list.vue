<template>
  <div>
    <div class="container">
      <el-table
          :data="tableData"
          style="width: 100%; margin-bottom: 20px"
          row-key="id"
          border
          default-expand-all
      >
        <el-table-column prop="name" label="名称" sortable/>
        <el-table-column prop="permission" label="权限标识" sortable/>
        <el-table-column prop="gender" label="类型" sortable>
          <template #default="{ row }">
            <span>{{ row.type === 'MENU' ? '菜单' : '按钮' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="url" label="地址" sortable/>
        <el-table-column prop="icon" align="center" label="图标" sortable>
          <template #default="{ row }">
            <el-icon>
              <component :is="row.icon"></component>
            </el-icon>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="400" fixed="right">
          <template #default="scope">
            <el-button-group>
              <el-button text :icon="Edit" @click="handleEdit(scope.row)" v-action:resource:update>
                编辑
              </el-button>
              <el-button text :icon="Edit" @click="handleAdd(scope.row)" v-action:resource:create>
                新增同级
              </el-button>
              <el-button text :icon="Edit" @click="handleAdd(scope.row,true)" v-action:resource:create>
                新增下级
              </el-button>
              <el-button text :icon="Delete" class="red" @click="handleDelete(scope.row)" v-action:resource:delete>
                删除
              </el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 新增弹出框 -->
    <el-dialog title="新增" v-model="addVisible" width="35%">
      <el-form label-width="70px">
        <el-form label-width="70px">
          <el-form-item label="上级节点">
            <el-input v-model="form.parentName" disabled></el-input>
          </el-form-item>
          <el-form-item label="名称">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="权限标识">
            <el-input v-model="form.permission"></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="region">
            <el-select v-model="form.type" placeholder="请选择">
              <el-option key="MENU" label="菜单" value="MENU"></el-option>
              <el-option key="BUTTON" label="按钮" value="BUTTON"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="地址" v-if="form.type==='MENU'">
            <el-input v-model="form.url"></el-input>
          </el-form-item>
          <el-form-item label="图标" v-if="form.type==='MENU'">
            <el-input v-model="form.icon" readonly>
              <template #prepend>
                <el-button :icon="form.icon"></el-button>
              </template>
            </el-input>
            <div>
              <AppIcon v-model="form.icon" :selected-icon="form.icon" @change="handleAppIconChange"/>
            </div>
          </el-form-item>
        </el-form>
      </el-form>
      <template #footer>
				<span class="dialog-footer">
					<el-button @click="addVisible = false">取 消</el-button>
					<el-button type="primary" @click="saveAdd">确 定</el-button>
				</span>
      </template>
    </el-dialog>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" v-model="editVisible" width="40%">
      <el-form label-width="70px">
        <el-form label-width="70px">
          <el-form-item label="上级节点">
            <el-input v-model="form.parentName" disabled></el-input>
          </el-form-item>
          <el-form-item label="名称">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="权限标识">
            <el-input v-model="form.permission"></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="region">
            <el-select v-model="form.type" placeholder="请选择">
              <el-option key="MENU" label="菜单" value="MENU"></el-option>
              <el-option key="BUTTON" label="按钮" value="BUTTON"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="地址" v-if="form.type==='MENU'">
            <el-input v-model="form.url"></el-input>
          </el-form-item>
          <el-form-item label="图标" v-if="form.type==='MENU'">
            <el-input v-model="form.icon" readonly>
              <template #prepend>
                <el-button :icon="form.icon"></el-button>
              </template>
            </el-input>
            <div>
              <AppIcon v-model="form.icon" :model-value="form.icon" :selected-icon="form.icon"
                       @change="handleAppIconChange"/>
            </div>
          </el-form-item>
        </el-form>
      </el-form>
      <template #footer>
				<span class="dialog-footer">
					<el-button @click="editVisible = false">取 消</el-button>
					<el-button type="primary" @click="saveEdit">确 定</el-button>
				</span>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" setup>
import {reactive, ref} from "vue";
import {Delete, Edit} from '@element-plus/icons-vue';
import {createResource, deleteResource, getResourceTree as reqResourceTree, updateResource} from "../api/resource";
import {ElMessage, ElMessageBox} from "element-plus";
import {FIRST_ICON} from "../components/AppIcon.vue";

const tableData = ref<Resource[]>([]);
const addVisible = ref<boolean>(false);
const editVisible = ref<boolean>(false);

let id: number = -1;

class Resource {
  name = '';
  type = 'MENU';
  url? = '';
  icon? = FIRST_ICON;
  permission = '';
  parentId = 1;
  parentName = '';
}

const handleAppIconChange = (icon: string) => {
  form.icon = icon
}

let form = reactive(new Resource());

const handleEdit = (record: any) => {
  id = record.id;
  form.name = record.name;
  form.type = record.type;
  form.url = record.url;
  form.icon = record.icon;
  form.permission = record.permission;
  form.parentId = record.parentId;
  form.parentName = record.parentName;
  editVisible.value = true;
}

const handleAdd = (record: any, isChildNode = false) => {
  Object.assign(form, new Resource());
  if (isChildNode) {
    form.parentId = record.id;
    form.parentName = record.name;
  } else {
    form.parentId = record.parentId;
    form.parentName = record.parentName;
  }
  addVisible.value = true;
}

const handleDelete = (record: any) => {
  // 二次确认删除
  ElMessageBox.confirm('确定要删除吗？', '提示', {
    type: 'warning'
  }).then(() => {
    deleteResource(record.id).then(res => {
      getResourceTree();
      ElMessage.success('删除成功');
    });
  })
      .catch(() => {
      });
}

const saveAdd = () => {
  const isMenu = form.type == 'MENU';
  createResource({...form, url: isMenu ? form.url : undefined, icon: isMenu ? form.icon : undefined}).then(res => {
    getResourceTree();
    ElMessage.success(`编辑成功`);
    addVisible.value = false;
  });
}

const saveEdit = () => {
  const isMenu = form.type == 'MENU';
  updateResource(id, {...form, url: isMenu ? form.url : undefined, icon: isMenu ? form.icon : undefined}).then(res => {
    getResourceTree();
    ElMessage.success(`编辑成功`);
    editVisible.value = false;
  });
}

const getResourceTree = () => {
  reqResourceTree().then(res => {
    tableData.value = res.data;
  });
}
getResourceTree();

</script>
<style scoped>
.red {
  color: #F56C6C;
}
</style>
