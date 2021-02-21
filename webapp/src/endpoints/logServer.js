import axios from 'axios';

export const logServer = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 0,
    withCredentials: false
});

export const logApi = {

};