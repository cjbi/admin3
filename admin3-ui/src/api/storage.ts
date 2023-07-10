import request from '../utils/request';
import {BASE_URI} from './base';

export function getStorageConfigList() {
  return request({
    url: `${BASE_URI}/storage/configs`,
    method: 'get'
  });
}

export function createStorageConfig(data: {
  name: string;
  type: string;
  endpoint?: string,
  accessKey?: string,
  secretKey?: string,
  bucketName?: string,
  address?: string,
  storagePath?: string
}) {
  return request({
    url: `${BASE_URI}/storage/configs`,
    method: 'post',
    data: data
  });
}

export function updateStorageConfig(id: number, data: {
  name: string;
  type: string;
  endpoint?: string,
  accessKey?: string,
  secretKey?: string,
  bucketName?: string,
  address?: string,
  storagePath?: string
}) {
  return request({
    url: `${BASE_URI}/storage/configs/${id}`,
    method: 'put',
    data: data
  });
}

export function deleteStorageConfig(id: number) {
  return request({
    url: `${BASE_URI}/storage/configs/${id}`,
    method: 'delete',
  });
}

export function markAsDefaultConfig(id: number) {
  return request({
    url: `${BASE_URI}/storage/configs/${id}:markAsDefault`,
    method: 'post',
  });
}

export function upload(data: { storageId?: string, files: any }) {
  return request({
    url: `${BASE_URI}/storage/upload`,
    headers: {'Content-Type': 'multipart/form-data'},
    method: 'post',
    data: data
  });
}

