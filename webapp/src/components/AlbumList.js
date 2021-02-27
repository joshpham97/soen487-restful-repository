import '../styles/albumList.css';

import React from 'react';
import { useState, useEffect } from 'react';
import { CircularProgress } from '@material-ui/core';

import Navbar from './Navbar';
import Album from './Album';
import {albumApi, albumServer} from "../endpoints/albumServer";

function AlbumList() {
    const [loaded, setLoaded] = useState(false);
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
            .then(res => setAlbums(res.data))
            .catch(err => {
                console.log(err);
                setAlbums(null);
            })
            .finally(() => setLoaded(true));
    };

    const renderAlbums = () => {
        if(!loaded)
            return <CircularProgress color="inherit" />
        else if(!albums)
            return <span>An error occurred while getting the albums</span>;
        else if(albums.length === 0)
            return <span>There are no albums yet</span>;

        return albums.map(album => (
            <div className="album">
            <Album key={album.isrc} isrc={album.isrc} title={album.title} releaseYear={album.releaseYear}
                   firstname={album.artist.firstname} lastname={album.artist.lastname} contentDesc={album.contentDesc}/>
            </div>
        ));
    };

    return (
        <React.Fragment>
            <Navbar />

            <div id="albumList">
                <h3>Albums</h3>

                <div className="albums">
                    {renderAlbums()}
                </div>
            </div>
        </React.Fragment>
    );
}

export default AlbumList;
