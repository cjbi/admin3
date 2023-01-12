import request from '../utils/request';
import {BASE_URI} from "./base";

export function getMenus() {
    return request({
        url: `${BASE_URI}/resources/menu`,
        method: 'get',
    });
}

export function getResourceTree() {
    return request({
        url: `${BASE_URI}/resources/tree`,
        method: 'get',
    });
}

export function createResource(data: { name: string, type: string, url?: string, icon?: string, permission: string, parentId: number }) {
    return request({
        url: `${BASE_URI}/resources`,
        method: 'post',
        data: data
    });
}

export function updateResource(resourceId: number, data: { name: string, type: string, url?: string, icon?: string, permission: string, parentId: number }) {
    return request({
        url: `${BASE_URI}/resources/${resourceId}`,
        method: 'put',
        data: data
    });
}

export function deleteResource(resourceId: number) {
    return request({
        url: `${BASE_URI}/resources/${resourceId}`,
        method: 'delete'
    });
}
