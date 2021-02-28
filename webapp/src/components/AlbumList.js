import '../styles/albumList.css';

import React from 'react';
import { useState, useEffect } from 'react';
import { withStyles } from '@material-ui/core/styles';
import { CircularProgress, Slide } from '@material-ui/core';
import MuiAddCircleOutlineRoundedIcon from '@material-ui/icons/AddCircleOutlineRounded';

import Navbar from './Navbar';
import AlbumSummary from './AlbumSummary';
import AlbumDetails from './AlbumDetails';
import { albumApi, albumServer } from "../endpoints/albumServer";

const AddCircleOutlineRounded = withStyles({
    root: {
        cursor: 'pointer'
    }
})(MuiAddCircleOutlineRoundedIcon);

function AlbumList(props) {
    const [loaded, setLoaded] = useState(false);
    const [albums, setAlbums] = useState([]);
    const [current, setCurrent] = useState(null);

    useEffect( () => {
        // Mount
        if(props.location.state && props.location.state.isrc)
            getAlbum();

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

    const getAlbum = () => {
        albumServer.get(albumApi.get + '/' + props.location.state.isrc)
            .then(res => setCurrent(res.data))
            .catch(err => alert(err));
    };

    const handleDetails = (album) => {
        if(current && current.isrc === album.isrc)
            setCurrent(null);
        else
            setCurrent(album);
    };

    const handleDetailsClose = () => {
        setCurrent(null);
    };

    const addRedirect = () => {
        props.history.push('/album/add');
    };

    const editRedirect = () => {
        props.history.push({
            pathname: '/album/edit',
            state: {
                isrc: current.isrc,
                title: current.title,
                firstname: current.artist.firstname,
                lastname: current.artist.lastname,
                releaseYear: current.releaseYear,
                contentDesc: current.contentDesc
            }
        });
    };

    const renderAlbums = () => {
        if(!loaded)
            return <CircularProgress color="inherit" />
        else if(!albums)
            return <div className="w-100 text-center">An error occurred while getting the albums</div>;
        else if(albums.length === 0)
            return (
                <React.Fragment>
                    <div className="w-100 text-center">There are no albums yet</div>
                    <AddCircleOutlineRounded fontSize="large" onClick={addRedirect} />
                </React.Fragment>
            );

        return (
            <React.Fragment>
                <div className="albums">
                    {albums.map(album => (
                        <div key={album.isrc} className="album" onClick={() => handleDetails(album)}>
                            <AlbumSummary title={album.title} firstname={album.artist.firstname} lastname={album.artist.lastname} />
                        </div>
                    ))}
                </div>

                <AddCircleOutlineRounded fontSize="large" onClick={addRedirect} />
            </React.Fragment>
        );
    };

    const renderAlbumDetails = () => {
        if(current)
            return <AlbumDetails title={current.title} firstname={current.artist.firstname} lastname={current.artist.lastname}
                             isrc={current.isrc} releaseYear={current.releaseYear} contentDesc={current.contentDesc}
                             handleClose={handleDetailsClose} handleEdit={editRedirect} />;
    };

    return (
        <React.Fragment>
            <Navbar />

            <div id="albumList">
                <h3>Albums</h3>

                {renderAlbums()}

                <Slide direction="up" in={current != null} mountOnEnter unmountOnExit>
                    <div className="albumDetails">
                        {renderAlbumDetails()}
                    </div>
                </Slide>
            </div>
        </React.Fragment>
    );
}

export default AlbumList;
