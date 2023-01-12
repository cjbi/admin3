import request from '../utils/request';
import {BASE_URI} from "./base";

export const fetchUserinfo = () => {
    return request({
        url: `${BASE_URI}/userinfo`,
        method: 'get'
    });
}
