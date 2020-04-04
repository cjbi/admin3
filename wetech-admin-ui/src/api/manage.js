import { axios } from '@/utils/request'

const api = {
  user: '/user',
  lockUser: '/user/{id}/lock',
  role: '/role',
  service: '/service',
  permissionNoPager: '/permission/no-pager',
  permission: '/permission',
  permissionTree: '/permission/tree',
  orgTree: '/org/tree'
}

export default api

export function deletePermission (parameter) {
  return axios({
    url: api.permission + '/' + parameter,
    method: 'delete'
  })
}

export function updatePermission (data) {
  return axios({
    url: api.permission,
    method: 'put',
    data: data
  })
}

export function createPermission (data) {
  return axios({
    url: api.permission,
    method: 'post',
    data: data
  })
}

export function deleteRole (parameter) {
  return axios({
    method: 'DELETE',
    url: api.role + '/' + parameter
  })
}

export function saveRole (data) {
  return axios({
    url: api.role,
    method: data.id ? 'put' : 'post',
    data: data
  })
}

export function getUserList (parameter) {
  return axios({
    url: api.user,
    method: 'get',
    params: parameter
  })
}

export function deleteUser (data) {
  return axios({
    method: 'delete',
    url: api.user,
    data: data
  })
}

export function lockUser (parameter) {
  return axios({
    url: api.lockUser.replace('{id}', parameter.id),
    method: 'put',
    params: { locked: parameter.locked }
  })
}

export function getPermissionTree () {
  return axios({
    url: api.permissionTree,
    method: 'get'
  })
}

export function getRoleList (parameter) {
  return axios({
    url: api.role,
    method: 'get',
    params: parameter
  })
}

export function createUser (parameter) {
  return axios({
    url: api.user,
    method: 'post',
    data: parameter
  })
}

export function updateUser (parameter) {
  return axios({
    url: api.user,
    method: 'put',
    data: parameter
  })
}

// 以下方法准备删除

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
