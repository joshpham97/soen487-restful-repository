import axios from 'axios';

export const albumServer = axios.create({
    baseURL: 'http://localhost:8081',
    timeout: 0,
    withCredentials: false
});

export const albumApi = {
    albums: '/myapp/album',
    get: '/myapp/album',
    add: '/myapp/album',
    update: '/myapp/album',
    delete: '/myapp/album',
    getAlbumCover: '/myapp/albumImage',
    addAlbumCover: '/myapp/albumImage',
    deleteAlbumCover: '/myapp/albumImage'
};