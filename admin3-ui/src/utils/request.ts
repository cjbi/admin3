import axios, {AxiosError, AxiosInstance, AxiosRequestConfig, AxiosResponse} from 'axios';
import {ElMessage} from "element-plus";
import router from "../router";

const service: AxiosInstance = axios.create({
    timeout: 5000
});

service.interceptors.request.use(
    (config: AxiosRequestConfig) => {
        const headerToken = localStorage.getItem('token');
        if (headerToken) {
            // @ts-ignore
            config.headers['Authorization'] = 'Bearer ' + headerToken
        }

        return config;
    },
    (error: AxiosError) => {
        console.log(error);
        return Promise.reject();
    }
);

service.interceptors.response.use(
    (response: AxiosResponse) => {
        if (response.status === 200) {
            return response;
        } else {
            Promise.reject();
        }
    },
    (error: AxiosError) => {
        console.log(error);
        if (error.response?.status === 401) {
            localStorage.removeItem('token');
            router.push('/login');
        }
        // @ts-ignore
        ElMessage.error(error.response?.data?.message);
        return Promise.reject();
    }
);

export default service;
