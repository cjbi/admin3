import request from '../utils/request';
import {BASE_URI} from './base';

export function getUserList(data: { page: number; size: number; username?: string, state?: string, roleId?: number }) {
    return request({
        url: `${BASE_URI}/users`,
        method: 'get',
        params: data
    });
};

export function createUser(data: { username: string; gender: string; avatar: string; }) {
    return request({
        url: `${BASE_URI}/users`,
        method: 'post',
        data: data
    });
}

export function updateUser(userId: number, data: { username: string; gender: string; avatar: string; }) {
    return request({
        url: `${BASE_URI}/users/${userId}`,
        method: 'put',
        data: data
    });
}

export function deleteUser(userId: number) {
    return request({
        url: `${BASE_URI}/users/${userId}`,
        method: 'delete'
    });
}

export function disableUser(userId: number) {
    return request({
        url: `${BASE_URI}/users/${userId}:disable`,
        method: 'post'
    });
}

export function enableUser(userId: number) {
    return request({
        url: `${BASE_URI}/users/${userId}:enable`,
        method: 'post'
    });
}
