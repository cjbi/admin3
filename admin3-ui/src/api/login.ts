import request from '../utils/request';
import {BASE_URI} from "./base";

export function login(data: { username: string; password: string }) {
    return request({
        url: `${BASE_URI}/login`,
        method: 'post',
        data: data
    });
}

export function logout() {
    return request({
        url: `${BASE_URI}/logout`,
        method: 'post',
    });
}
