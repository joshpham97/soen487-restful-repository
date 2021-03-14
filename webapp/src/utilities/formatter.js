export const formatter = {
    getCoverImageUrl: function(isrc, refresh){
        let timeStamp = new Date();
        let url = `http://localhost:8081/myapp/albumImage/${isrc}?${timeStamp.getTime()}`;
        return url;
    }
};