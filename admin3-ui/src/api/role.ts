import request from '../utils/request';
import {BASE_URI} from './base';

export function getRoleList() {
    return request({
        url: `${BASE_URI}/roles`,
        method: 'get'
    });
};

export function getRoleUserList(roleId: number, data: { page: number; size: number; username?: string, state?: string }) {
    return request({
        url: `${BASE_URI}/roles/${roleId}/users`,
        method: 'get',
        params: data
    });
};

export function createRole(data: { name: string; description: string; }) {
    return request({
        url: `${BASE_URI}/roles`,
        method: 'post',
        data: data
    });
}

export function updateRole(roleId: number, data: { name: string; description: string; }) {
    return request({
        url: `${BASE_URI}/roles/${roleId}`,
        method: 'put',
        data: data
    });
}

export function deleteRole(roleId: number) {
    return request({
        url: `${BASE_URI}/roles/${roleId}`,
        method: 'delete'
    });
}

export function changeUsers(roleId: number, userIds: number[]) {
    return request({
        url: `${BASE_URI}/roles/${roleId}/users`,
        method: 'put',
        data: {userIds}
    });
}


export function changeResources(roleId: number, resourceIds: number[]) {
    return request({
        url: `${BASE_URI}/roles/${roleId}/resources`,
        method: 'put',
        data: {resourceIds}
    });
}

