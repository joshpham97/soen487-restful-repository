import { useState, useEffect } from 'react';

import Navbar from './Navbar';
import {albumApi, albumServer} from "../endpoints/albumServer";

function Example2() {
    const [albums, setAlbums] = useState([]);

    useEffect(() => {
        // Mount
        getAlbums();
        const interval = setInterval(getAlbums, 1000);

        // Unmount
        return () => clearInterval(interval);
    }, []);

    const getAlbums = () => {
        albumServer.get(albumApi.albums)
            .then(res => {
                setAlbums(res.data);
            })
            .catch(err => {
                console.log(err);
                setAlbums(null)
            });
    };

    const renderAlbums = () => {
        if(!albums)
            return <div>An error occurred while getting the albums</div>;
        else if(albums.length === 0)
            return <div>There are no albums yet</div>;

        return albums.map(album => (
            <div key={album.isrc}>
                <span>ISRC: {album.isrc}</span>
                <span className="ml-3">Title: {album.title}</span>
                <span className="ml-3">Release Year: {album.releaseYear}</span>
                <span className="ml-3">Artist: {album.artist.firstname} {album.artist.lastname}</span>
                <span className="ml-3">Content Description: {album.contentDesc}</span>
            </div>
        ));
    }

    return (
        <div>
            <Navbar />

            <div className="mb-2">
                Example2
            </div>

            {renderAlbums()}
        </div>
    );
}

export default Example2;