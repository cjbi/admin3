import { axios } from '@/utils/request'

const api = {
  user: '/user',
  lockUser: '/user/lock',
  role: '/role',
  service: '/service',
  permission: '/permission',
  permissionNoPager: '/permission/no-pager',
  permissionTree: '/permission/tree',
  orgTree: '/org/tree'
}

export default api

export function getUserList (parameter) {
  return axios({
    url: api.user,
    method: 'get',
    params: parameter
  })
}

export function deleteUser (parameter) {
  return axios({
    method: 'delete',
    url: api.user,
    params: parameter
  })
}

export function lockUser (parameter) {
  return axios({
    url: api.lockUser,
    method: 'put',
    params: parameter
  })
}

export function getPermissionTree () {
  return axios({
    url: api.permissionTree,
    method: 'get'
  })
}

// 以下方法准备删除

export function getRoleList (parameter) {
  return axios({
    url: api.role,
    method: 'get',
    params: parameter
  })
}

export function getServiceList (parameter) {
  return axios({
    url: api.service,
    method: 'get',
    params: parameter
  })
}

export function getPermissions (parameter) {
  return axios({
    url: api.permissionNoPager,
    method: 'get',
    params: parameter
  })
}

export function getOrgTree (parameter) {
  return axios({
    url: api.orgTree,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveService (parameter) {
  return axios({
    url: api.service,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}
