<template>
  <div class="data-center-wrap">
    <el-card shadow="never" style="height: 100%">
      <el-row :gutter="20" style="height: 100%">
        <el-col :xl="4" :lg="4" style="border-right: 1px solid #dcdfe6">
          <div style="margin-bottom: 24px; font-weight: 700">角色管理</div>
          <el-divider></el-divider>
          <el-button @click="addVisible = true;Object.assign(form, new Role());" type="primary" link
                     v-action:role:create>新建角色
          </el-button>
          <div style="margin-top: 16px">
            <div
              class="role-item"
              :class="{ 'role-item-active': item.id === activeRoleId }"
              v-for="(item, index) in roleList"
              :key="index"
              @click="handleRoleChange(item)"
            >
              <div>
                <div>{{ item.name }}</div>
              </div>
              <el-dropdown trigger="hover">
                <el-icon>
                  <setting/>
                </el-icon>
                <template #dropdown>
                  <el-dropdown-menu>
                    <span v-action:role:update>
                      <el-dropdown-item @click="handleEdit" v-action:role:update>编辑</el-dropdown-item>
                    </span>
                    <span v-action:role:delete>
                      <el-dropdown-item @click="handleDelete(roleList.find(r => r?.id === activeRoleId))">
                        <span class="del-btn">删除</span>
                      </el-dropdown-item>
                    </span>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
          </div>
        </el-col>
        <el-col :xl="20" :lg="20">
          <el-col :span="24">
            <el-col :span="24" style="padding-left: 0px;">
              {{ roleList.find(r => r?.id === activeRoleId)?.name }}
              <el-button text :icon="Edit" @click="handleEdit" v-action:role:update>
                编辑
              </el-button>
              <el-button text :icon="Delete" class="red"
                         @click="handleDelete(roleList.find(r => r?.id === activeRoleId))" v-action:role:delete>
                删除
              </el-button>
            </el-col>
            <el-col style="font-size: 8px;color: #777777;padding-left: 0px;" :span="24">
              {{ roleList.find(r => r?.id === activeRoleId)?.description }}
            </el-col>
          </el-col>
          <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleChangeRoleUserClient">
            <el-tab-pane label="角色成员" name="first">
              <div style="margin-bottom: 24px">
                <el-button type="primary" @click="isOrgSelectShow = true" v-action:role:update>调整成员</el-button>
              </div>
              <el-table :data="tableData" border class="table" ref="multipleTable"
                        header-cell-class-name="table-header">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="username" label="用户名"></el-table-column>
                <el-table-column prop="gender" label="性别">
                  <template #default="{ row }">
                    <span>{{ row.gender === 'MALE' ? '男' : '女' }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="头像" align="center">
                  <template #default="scope">
                    <el-image
                      class="table-td-thumb"
                      :src="scope.row.avatar"
                      :z-index="10"
                      :preview-src-list="[scope?.row?.avatar]"
                      preview-teleported
                    >
                    </el-image>
                  </template>
                </el-table-column>
                <el-table-column label="拥有的角色">
                  <template #default="scope">
                    <sppan v-for="(role, index) in scope.row?.roles">
                      <template v-if="index > 0">, </template>
                      {{ role.name }}
                    </sppan>
                  </template>
                </el-table-column>
              </el-table>
              <div class="pagination">
                <el-pagination
                  background
                  layout="total, prev, pager, next"
                  :current-page="userQuery.pageIndex"
                  :page-size="userQuery.pageSize"
                  :total="pageTotal"
                  @current-change="handlePageChange"
                ></el-pagination>
              </div>
            </el-tab-pane>
            <el-tab-pane label="权限设置" name="second">
              <div style="margin-bottom: 24px">
                <el-button type="primary" @click="savePermission" v-action:role:update>保存</el-button>
              </div>
              <div v-for="(menu, index) in rolePermission" :key="index">
                <el-row>
                  <el-col :span="18" :style="{fontWeight:'bold'}">{{ menu.name }}</el-col>
                  <el-col :span="6" :style="{textAlign:'right'}">
                    <el-switch
                      size="small"
                      v-model="menu.enabled"
                      @change="onChangeSwitch($event,menu)"/>
                  </el-col>
                </el-row>
                <el-divider type="horizontal" :style="{margin: '5px'}"/>
                <el-row :gutter="16" v-for="(checkbox, tab) in menu.children" :key="tab">
                  <el-col :xl="4" :lg="4">
                    {{ checkbox.name }}：
                  </el-col>
                  <el-col :xl="20" :lg="20">
                    <el-checkbox
                      :disabled="!menu.enabled"
                      v-model="checkbox.checkedAll"
                      :indeterminate="checkbox.indeterminate"
                      @change="onChangeCheckAll($event,checkbox)"
                    >全选
                    </el-checkbox>
                    <el-checkbox-group :disabled="!menu.enabled" v-model="checkbox.selected"
                                       @change="onChangeCheck($event,checkbox)">
                      <el-checkbox v-for="btn in checkbox?.actionsOptions" :key="btn.id" :label="btn.id">
                        {{ btn.name }}
                      </el-checkbox>
                    </el-checkbox-group>
                  </el-col>
                </el-row>
              </div>

            </el-tab-pane>
          </el-tabs>
        </el-col>
      </el-row>
    </el-card>

    <!-- 组织架构选择弹出框 -->
    <OrgSelect
      append-to-body
      v-if="isOrgSelectShow"
      :visible="isOrgSelectShow"
      :activeRoleId="activeRoleId"
      @on-cancel="handleOrgSelectCancel"
      @on-submit="handleOrgSelectSubmit"
    />

    <!-- 新增弹出框 -->
    <el-dialog title="新增" v-model="addVisible" width="30%">
      <el-form label-width="70px">
        <el-form-item label="用户名">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
				<span class="dialog-footer">
					<el-button @click="addVisible = false">取 消</el-button>
					<el-button type="primary" @click="saveAdd">确 定</el-button>
				</span>
      </template>
    </el-dialog>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" v-model="editVisible" width="30%">
      <el-form label-width="70px">
        <el-form label-width="70px">
          <el-form-item label="用户名">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="描述">
            <el-input v-model="form.description" type="textarea"></el-input>
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
<script setup lang="ts">
import {reactive, ref} from "vue";
import {
  changeResources,
  changeUsers,
  createRole,
  deleteRole,
  getRoleList,
  getRoleUserList,
  updateRole
} from "../api/role";
import {ElMessage, ElMessageBox, TabsPaneContext} from "element-plus";
import {getResourceTree as reqResourceTree} from "../api/resource";
import {Delete, Edit} from '@element-plus/icons-vue';
import OrgSelect, {OrgSelectedData} from "../components/OrgSelect.vue";

const isOrgSelectShow = ref(false)

interface Resource {
  id: number;
  name: string;
  type: string;
  permission: string;
  url: string;
  children?: Resource[]
}

interface RoleInterface {
  id: number;
  name?: string;
  description?: string;
  resourceIds?: number[];
}

class Role implements RoleInterface {
  id = 0;
  name = '';
  description = '';
  resourceIds = [];
}

interface Permission {
  id: number;
  name: string;
  enabled: boolean,
  children: {
    id: number;
    name: string;
    checkedAll: boolean;
    indeterminate: boolean;
    selected: number[];
    actionsOptions: {
      id: number;
      name: string;
    }[]
  }[];
}

interface UserTableItem {
  id: number;
  userName: string;
  gender: string;
  state: string;
  roles: { id: number, name: string }
}

interface RoleSelectable extends RoleInterface {
  value: number;
  label?: string;
}

const roleList = ref<RoleInterface[]>([]);
const activeRoleId = ref<number>(1);


const reqRoleList = async () => {
  try {
    const { data, } = await getRoleList();
    roleList.value = data;
    getResourceTree(); // 获取权限列表
  } catch (error) {
    ElMessage.error(error as Error);
  }
};

reqRoleList();

const handleRoleChange = (role: RoleInterface) => {
  activeRoleId.value = role.id;
  getUserData(role.id);
  handlePermission();
}

//用户表格数据

const userQuery = reactive({
  username: '',
  state: '',
  pageIndex: 1,
  pageSize: 10
});
const tableData = ref<UserTableItem[]>([]);
const pageTotal = ref(0);

const getUserData = (roleId: number) => {
  getRoleUserList(roleId, {
    page: userQuery.pageIndex,
    size: userQuery.pageSize,
    username: userQuery.username || undefined,
    state: userQuery.state || undefined,
  }).then(res => {
    tableData.value = res.data.list;
    pageTotal.value = res.data.total;
  });
}
getUserData(activeRoleId.value);


// 分页导航
const handlePageChange = (val: number) => {
  userQuery.pageIndex = val;
  getUserData(activeRoleId.value);
};

//标签页
const activeName = ref('first');
const rolePermission = ref<Permission[]>([]);

const getResourceTree = () => {

//初始化权限资源树
  reqResourceTree().then(res => {
    rolePermission.value = res.data.map((menu: Resource) => {
      return {
        id: menu.id,
        name: menu.name,
        enabled: true,
        children: menu.children?.map((checkbox: Resource) => {
          return {
            id: checkbox.id,
            name: checkbox.name,
            checkedAll: false,
            indeterminate: false,
            selected: [],
            actionsOptions: checkbox.children?.map((p: Resource) => {
              return {
                id: p.id,
                name: p.name
              };
            }) || []
          };
        }) || []
      };
    });

    // 赋值
    handlePermission();
  });
}

function handlePermission() {
  let role: any = roleList.value.find(r => r.id == activeRoleId.value);
  const resourceIds: number[] = role.resourceIds || [];
  rolePermission.value.forEach(p => {
    onChangeSwitch(resourceIds.includes(p.id), p);
    p.children.forEach(pp => {
      const selected: any[] = pp.actionsOptions.filter(action => resourceIds.includes(action.id)).map(action => action.id);
      onChangeCheckAll(selected.length === pp.actionsOptions.length, pp);
      pp.selected = selected;
      onChangeCheck(selected, pp);
    });
  });
}

const onChangeSwitch = (enabled: boolean, permission: any) => {
  permission.enabled = enabled;
}

const onChangeCheckAll = (checked: boolean, permission: any) => {
  Object.assign(permission, {
    selected: checked ? permission.actionsOptions.map((obj: any) => obj.id) : [],
    indeterminate: false,
    checkedAll: checked
  });
}

const onChangeCheck = (selected: number[], permission: any) => {
  permission.indeterminate = !!permission.selected.length && (permission.selected.length < permission.actionsOptions.length);
  permission.checkedAll = permission.selected.length === permission.actionsOptions.length;
}

const handleChangeRoleUserClient = (tab: TabsPaneContext, event: Event) => {

}

const handleEdit = () => {
  let role: any = roleList.value?.find(r => r?.id === activeRoleId.value);
  form.name = role.name;
  form.description = role.description;
  editVisible.value = true;
};

const addVisible = ref<boolean>(false);
const editVisible = ref<boolean>(false);
let form = reactive(new Role());

const saveAdd = () => {
  createRole(form).then(res => {
    reqRoleList();
    ElMessage.success(`新增成功`);
    addVisible.value = false;
  });
};
const saveEdit = () => {
  updateRole(activeRoleId.value, form).then(res => {
    reqRoleList();
    ElMessage.success(`修改成功`);
    editVisible.value = false;
  });
};

const savePermission = () => {
  let resourceIds: number[] = [];
  rolePermission.value.forEach(p => {
    if (p.enabled) {
      resourceIds.push(p.id);
    }
    p.children.forEach(pp => {
      if (pp.selected?.length) {
        resourceIds.push(pp.id);
      }
      pp.selected.forEach(sel => resourceIds.push(sel));
    })
  });
  changeResources(activeRoleId.value, resourceIds).then(res => {
    reqRoleList();
    ElMessage.success(`修改角色权限成功`);
    editVisible.value = false;
  });
}

// 删除操作
const handleDelete = (role: any) => {
  // 二次确认删除
  ElMessageBox.confirm('确定要删除吗？', '提示', {
    type: 'warning'
  }).then(() => {
    deleteRole(role.id).then(res => {
      reqRoleList();
      ElMessage.success('删除成功');
    });
  }).catch(() => {
  });
};
const handleOrgSelectCancel = () => {
  isOrgSelectShow.value = false;
}
const handleOrgSelectSubmit = (p: OrgSelectedData) => {
  isOrgSelectShow.value = false;
  console.log(p);
  debugger
  let userIds: number[] = p.selected?.map((u: { id: number; }) => u.id);
  changeUsers(activeRoleId.value, userIds).then(res => {
    getUserData(activeRoleId.value);
    ElMessage.success(`修改角色成员成功`);
    editVisible.value = false;
  });
}

</script>
<style scoped lang="scss">
.data-center-wrap {
  position: relative;

  .role-item {
    font-size: 14px;
    height: 32px;
    line-height: 32px;
    cursor: pointer;
    white-space: nowrap;
    display: flex;
    align-items: center;
    padding: 0 4px;

    &:hover {
      background-color: #f5f7fa;
    }

    &-active {
      background-color: #ecf5ff;
    }

    .el-icon {
      margin-left: 4px;
    }
  }

  .el-pagination {
    display: flex;
    justify-content: flex-end;
    margin: 20px 0;
    padding-bottom: 20px;
  }
}

.red {
  color: #F56C6C;
}

.table-td-thumb {
  display: block;
  margin: auto;
  width: 40px;
  height: 40px;
}
</style>
