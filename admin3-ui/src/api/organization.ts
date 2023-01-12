import request from '../utils/request';
import {BASE_URI} from "./base";

export function getOrganizationTree(params: { parentId: number }) {
    return request({
        url: `${BASE_URI}/organizations/tree`,
        method: 'get',
        params: params
    });
}

export function getOrganizationUserList(organizationId: number, data: { page: number; size: number; username?: string, state?: string, roleId?: number }) {
    return request({
        url: `${BASE_URI}/organizations/${organizationId}/users`,
        method: 'get',
        params: data
    });
};

export function createOrganization(data: { name: string, type: any, parentId: number }) {
    return request({
        url: `${BASE_URI}/organizations`,
        method: 'post',
        data: data
    });
}

export function updateOrganization(organizationId: number, data: { name: string }) {
    return request({
        url: `${BASE_URI}/organizations/${organizationId}`,
        method: 'put',
        data: data
    });
}

export function deleteOrganization(organizationId: number) {
    return request({
        url: `${BASE_URI}/organizations/${organizationId}`,
        method: 'delete'
    });
}
