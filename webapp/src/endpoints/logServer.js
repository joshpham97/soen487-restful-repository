import axios from 'axios';

export const logServer = axios.create({
    baseURL: 'http://localhost:8082',
    timeout: 0,
    withCredentials: false
});

export const logApi = {
    get: '/logapp/log',
    delete: '/logapp/log'
};