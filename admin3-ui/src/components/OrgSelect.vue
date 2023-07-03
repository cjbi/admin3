<style scoped lang="scss">
.org-content {
  display: flex;
  height: 500px;

  .org-left {
    width: 325px;
    border-right: 1px solid rgba(0, 0, 0, 0.1);
    padding-right: 16px;
    overflow: auto;
  }

  .org-center {
    padding: 0 8px;
    width: 125px;
    border-right: 1px solid rgba(0, 0, 0, 0.1);
    padding-right: 16px;
  }

  .org-right {
    padding-left: 16px;
    flex: 1;
    line-height: 32px;
    overflow: auto;
  }
}

.org-footer {
  text-align: center;
}

.org-header {
  text-align: center;
  font-size: 17px;
}

.el-tag {
  margin-right: 8px;
}

// :deep(.el-dialog__body) {
//   border-top: 1px solid rgba(0,0,0,.06);
//   border-bottom: 1px solid rgba(0,0,0,.06);
// }
</style>

<style>
.org-select .el-dialog__body {
  padding-top: 0;
  padding-bottom: 0;
  border-top: 1px solid rgba(0, 0, 0, 0.1);
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}
</style>

<template>
  <el-dialog
      class="org-select"
      width="700px"
      :model-value="visible"
      :show-close="false"
      :close-on-click-modal="false"
      v-bind="$attrs"
  >
    <template #header>
      <div class="org-header">组织架构</div>
    </template>
    <div class="org-content">
      <div class="org-left">
        <el-tabs v-model="activeTabName">
          <el-tab-pane label="组织架构" name="org" v-if="tabType !== TabType.ROLE">
            <el-input
                style="margin-bottom: 8px"
                :prefix-icon="Search"
                v-model="orgSearchVal"
                placeholder="搜索"
                clearable
            />
            <el-tree
                :data="orgList"
                :show-checkbox="!filterUser"
                highlight-current
                node-key="id"
                default-expand-all
                :props="defaultProps"
                :expand-on-click-node="false"
                @check="handleCheck"
                @node-click="handleNodeClick"
                ref="orgTreeRef"
            >
              <template #default="{ data }">
                <el-icon v-if="data.type === OrgTypeEum.DEPART">
                  <office-building/>
                </el-icon>
                <svg-icon v-else icon-class="job"></svg-icon>
                <span style="margin: 0 4px">{{ data.name }}</span>
              </template>
            </el-tree>
          </el-tab-pane>
        </el-tabs>
      </div>
      <!-- <div v-if="filterUser" class="center">
        <el-radio-group v-model="selectedUserId">
          <el-radio v-for="(item, index) in userList" :key="index" :label="item.id">{{
            item.username
          }}</el-radio>
        </el-radio-group>
      </div> -->
      <div class="org-right">
        <div>{{ mode === ModeType.MULT ? '已选择' : '请选择' }}</div>

        <div v-if="mode === ModeType.MULT">
          <div v-if="filterUser">
            <el-checkbox
                v-if="userList.length"
                :indeterminate="
                selectedUserIds.length > 0 && selectedUserIds.length < userList.length
              "
                @change="toggleSelectAllUsers"
            >全选
            </el-checkbox
            >
            <el-checkbox-group v-model="selectedUserIds" @change="handleChange">
              <el-checkbox v-for="(item, index) in userList" :key="index" :label="item.id">
                {{ item.username }}
              </el-checkbox>
            </el-checkbox-group>
          </div>
          <div v-else>
            <div v-if="activeTabName === TabType.ORG">
              <el-tag v-for="(orgItem, index) in selectedOrgNames" :key="index" closable>
                {{ orgItem }}
              </el-tag>
            </div>
            <div v-else>
              <el-tag v-for="(rolItem, index) in selectedRoleNames" :key="index" closable>
                {{ rolItem }}
              </el-tag>
            </div>
          </div>
        </div>

        <div v-else-if="mode === ModeType.RADIO">
          <div v-if="filterUser">
            <el-radio-group v-model="selectedUserId" @change="handleChange">
              <el-radio v-for="(item, index) in userList" :key="index" :label="item.id">
                {{ item.username }}
              </el-radio>
            </el-radio-group>
          </div>
          <div v-else>
            <div v-if="activeTabName === TabType.ORG">
              <el-radio-group v-model="singleSelectedOrgId">
                <el-radio v-for="(item, index) in selectedOrg" :key="index" :label="item.id">{{
                    item.name
                  }}
                </el-radio>
              </el-radio-group>
            </div>
            <div v-else>
              <el-radio-group v-model="singleSelectedRoleId">
                <el-radio v-for="(item, index) in selectedRole" :key="index" :label="item.id">
                  {{ item.name }}
                </el-radio>
              </el-radio-group>
            </div>
          </div>
        </div>
      </div>
    </div>
    <template #footer>
      <div class="footer">
        <el-button @click="onCancel">取消</el-button>
        <el-button type="primary" @click="onSubmit">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script lang="ts">
import {computed, defineComponent, PropType, reactive, ref, watchEffect} from 'vue'
import {Search} from '@element-plus/icons-vue'
import {ElTree} from 'element-plus'
import {getOrganizationTree, getOrganizationUserList} from "../api/organization";
import { getRoleUserList,} from "../api/role";

export enum OrgTypeEum {
  DEPART = 'DEPARTMENT',
  JOB = 'JOB',
}

export interface OrgTreeNode {
  name: string
  id: number
  children?: OrgTreeNode[]
  leader?: { username: string; userId: number }
  type?: OrgTypeEum
  isLeaf?: boolean
  parent?: OrgTreeNode
}

export const rootNode = {name: '根节点', id: 1, type: OrgTypeEum.DEPART, children: []}

export const defaultProps = {
  children: 'children',
  label: 'name',
  isLeaf: 'isLeaf',
}

export enum ModeType {
  RADIO = 'radio', // 单选
  MULT = 'multiple', // 多选
}

export enum TabType {
  ORG = 'org',
  ROLE = 'role',
  ALL = 'all',
}

export type OrgSelectedData = {
  type: 'org' | 'role' | 'user'
  selected: Record<string, any> | Record<string, any>[]
}

export type CheckedStatus = {
  checkedNodes: OrgTreeNode[]
  checkedKeys: number[]
  halfCheckedNodes: OrgTreeNode[]
  halfCheckedKeys: number[]
}

interface UserTableItem {
  id: number;
  userName: string;
  username: string;
  gender: string;
  state: string;
  roles: { id: number, name: string }
}

export default defineComponent({
  name: 'OrgSelect',
  props: {
    visible: {
      type: Boolean,
      required: true,
    },
    // 组织架构和角色的显示情况
    tabType: {
      type: String as PropType<TabType>,
      required: false,
      default: TabType.ORG,
    },
    mode: {
      type: String as PropType<ModeType>,
      required: false,
      default: ModeType.MULT,
    },
    // 是否筛选用户
    filterUser: {
      type: Boolean,
      required: false,
      default: true,
    },
    // filterUser=true时使用
    selectedUsers: {
      type: Array as PropType<any[]>,
      default: () => [],
    },
    // 已选择的角色id 回显角色下的所有人
    activeRoleId: {
      type: Number,
      default: 0
    }
  },
  emits: ['on-submit', 'on-cancel', 'change'],
  setup(props, {emit}) {
    const orgTreeRef = ref<typeof ElTree>(),
        roleTreeRef = ref<typeof ElTree>()
    const selectedUserIds = ref<number[]> ([])
    const activeTabName = ref<TabType>(props.tabType === TabType.ALL ? TabType.ORG : props.tabType)
    const orgSearchVal = ref(''),
        roleSearchVal = ref('')
    const selectedOrg = ref<OrgTreeNode[]>([]),
        selectedRole = ref<any[]>([])
    const singleSelectedOrgId = ref<number | null>(null),
        singleSelectedRoleId = ref<number | null>(null)
    const selectedOrgNames = computed(() => selectedOrg.value.map((i) => i.name))
    const selectedRoleNames = computed(() => selectedRole.value.map((i) => i.name))
    const userQuery = reactive({
      pageIndex: 1,
      pageSize: 1000
    });
    const selectedUsers = ref<UserTableItem[]>([]);

    const orgList = ref<OrgTreeNode[]>([rootNode])
    const reqOrgList = async () => {
      const res = await getOrganizationTree({
        parentId: rootNode.id,
      })
      orgList.value[0].children = res.data
    }
    reqOrgList()

    const getUserData = (roleId: number) => {
      getRoleUserList(roleId, {
        page: userQuery.pageIndex,
        size: userQuery.pageSize,
      }).then(res => {
        selectedUsers.value = res.data.list;
        selectedUserIds.value = selectedUsers.value.map(i => i.id)
      });
    }

    getUserData(props.activeRoleId);

    const selectedNode = ref<OrgTreeNode | null>(null)
    const handleNodeClick = (data: OrgTreeNode) => {
      selectedNode.value = data
    }
    const handleCheck = (checkedNode: OrgTreeNode, checkedStatus: CheckedStatus) => {
      const {halfCheckedKeys, checkedKeys} = checkedStatus
      if (activeTabName.value === TabType.ORG) {
        if (halfCheckedKeys.length === 0 && checkedKeys.length) {
          selectedOrg.value = [rootNode]
        } else {
          const needToRemoveIds = removeAllCheckedChildren(checkedStatus)
          selectedOrg.value = checkedStatus.checkedNodes.filter(
              (i) => !needToRemoveIds.includes(i.id),
          )
        }
      } else {
        selectedRole.value = checkedStatus.checkedNodes
      }
    }
    // 将全部选择了子节点去掉，使用其父节点
    const removeAllCheckedChildren = (checkedStatus: CheckedStatus) => {
      const {checkedKeys, checkedNodes} = checkedStatus
      const needToRemoveIds: number[] = []
      for (const n of checkedNodes) {
        if (n.children?.length) {
          const allChildrenIds = n.children.map((i) => i.id)
          if (allChildrenIds.every((i) => checkedKeys.includes(i))) {
            needToRemoveIds.push(...allChildrenIds)
          }
        }
      }
      return needToRemoveIds
    }

    const userList = ref<any[]>(
        props.selectedUsers
            .filter(Boolean)
            .map((i) => ({...i, name: i.username, id: i.id || i.userId})),
    )
    const selectedUserId = ref<number | null>(
        props.selectedUsers[0]?.userId || props.selectedUsers[0]?.id || null,
    )
    // const selectedUserIds = ref<number[]>(
    //     props.selectedUsers.filter(Boolean).map((i) => i.id || i.useId),
    // )

    const reqUserList = async () => {
      const res = await getOrganizationUserList(selectedNode.value?.id || 1, {
        page: 1,
        size: 1000,
      })
      userList.value = res.data.list;
    }
    watchEffect(() => {
      if (selectedNode.value?.id!) {
        reqUserList()
      }
    })

    const onCancel = () => {
      selectedOrg.value = []
      selectedRole.value = []
      orgSearchVal.value = ''
      roleSearchVal.value = ''
      singleSelectedOrgId.value = null
      singleSelectedRoleId.value = null
      orgTreeRef.value?.setCheckedKeys([])
      roleTreeRef.value?.setCheckedKeys([])
      emit('on-cancel')
    }

    const genSelected = () => {
      let selected = null
      if (props.filterUser) {
        if (props.mode === ModeType.MULT) {
          selected = userList.value
              .filter((i) => selectedUserIds.value.includes(i.id))
              .map((i) => ({...i, name: i.username}))
        } else {
          selected = userList.value.find((i) => i.id === selectedUserId.value)
          if (selected) {
            selected.name = selected.username
          }
        }
      } else {
        if (activeTabName.value === TabType.ORG) {
          selected =
              props.mode === ModeType.MULT
                  ? selectedOrg.value
                  : selectedOrg.value.find((i) => i.id === singleSelectedOrgId.value)
        } else {
          selected =
              props.mode === ModeType.MULT
                  ? selectedRole.value
                  : selectedRole.value.find((i) => i.id === singleSelectedRoleId.value)
        }
      }
      return selected
    }

    const onSubmit = () => {
      emit('on-submit', {
        type: props.filterUser ? 'user' : activeTabName.value,
        selected: genSelected(),
      })
    }

    const handleChange = () => {
      emit('change', {
        type: props.filterUser ? 'user' : activeTabName.value,
        selected: genSelected(),
      })
    }

    const toggleSelectAllUsers = (val: boolean) => {
      if (val) {
        selectedUserIds.value = userList.value.map((i) => i.id)
      } else {
        selectedUserIds.value = []
      }
      emit('change', {
        type: props.filterUser ? 'user' : activeTabName.value,
        selected: genSelected(),
      })
    }

    return {
      Search,
      orgTreeRef,
      roleTreeRef,
      OrgTypeEum,
      ModeType,
      TabType,

      activeTabName,
      orgSearchVal,
      roleSearchVal,
      orgList,
      defaultProps,
      onCancel,
      onSubmit,
      handleNodeClick,
      handleCheck,
      selectedOrg,
      selectedRole,
      selectedOrgNames,
      selectedRoleNames,
      singleSelectedOrgId,
      singleSelectedRoleId,

      userList,
      selectedUserId,
      selectedUserIds,

      handleChange,

      toggleSelectAllUsers,
    }
  },
})
</script>
