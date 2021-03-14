import { serverConfig } from '../serverConfig';

export const formatter = {
    getCoverImageUrl: function(isrc){
        console.log("Isrc is: " + isrc);
        return `${serverConfig.basrUrl}${serverConfig.albumApi.getAlbumCover}/${isrc}`;
    }
};