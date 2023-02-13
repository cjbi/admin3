import {BASE_URI} from './base';
import request from "../utils/request";

export function getLogList(data: { page: number; size: number; typeNames?: string|undefined }) {
  return request({
    url: `${BASE_URI}/logs`,
    method: 'get',
    params: data,
  });
};

export function cleanLogs() {
  return request({
    url: `${BASE_URI}/logs`,
    method: 'delete'
  });
}
