import '../styles/albumList.css';

import React from 'react';
import { useState, useEffect } from 'react';
import { withStyles } from '@material-ui/core/styles';
import { CircularProgress, Fab, Slide } from '@material-ui/core';
import MuiAddIcon from '@material-ui/icons/Add';

import Navbar from './Navbar';
import AlbumSummary from './AlbumSummary';
import AlbumDetails from './AlbumDetails';
import { albumApi, albumServer } from "../endpoints/albumServer";

const AddIcon = withStyles({
    root: {
        cursor: 'pointer'
    }
})(MuiAddIcon);

function AlbumList(props) {
    const [loaded, setLoaded] = useState(false);
    const [albums, setAlbums] = useState([]);
    const [currentElement, setCurrentElement] = useState(null);
    const [currentAlbum, setCurrentAlbum] = useState(null);

    useEffect( () => {
        // Mount
        if(props.location.state && props.location.state.isrc)
            getAlbum();

        getAlbumList();
        const interval = setInterval(getAlbumList, 1000);

        // Unmount
        return () => clearInterval(interval);
    }, []);

    const getAlbumList = () => {
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
            .then(res => {
                setCurrentAlbum(res.data)
            })
            .catch(err => alert(err));
    };

    const toggleDetails = (e, album) => {
        // add/remove class on click
        toggleHighlight(e.currentTarget, album);

        // Set current album to trigger details
        if(currentAlbum && currentAlbum.isrc === album.isrc)
            setCurrentAlbum(null);
        else
            setCurrentAlbum(album);
    };

    const toggleHighlight = (target, album) => {
        if(!currentElement && currentAlbum && currentAlbum == album.isrc)
            return;

        if(currentElement)
            currentElement.firstChild.classList.remove("current");

        if(target && currentElement != target) {
            target.firstChild.classList.add("current");
            setCurrentElement(target);
        }
        else
            setCurrentElement(null);
    };

    const closeDetails = () => {
        toggleHighlight(null, null);
        setCurrentAlbum(null);
    };

    const addRedirect = () => {
        props.history.push('/album/add');
    };

    const editRedirect = () => {
        props.history.push({
            pathname: '/album/edit',
            state: {
                isrc: currentAlbum.isrc,
                title: currentAlbum.title,
                firstname: currentAlbum.artist.firstname,
                lastname: currentAlbum.artist.lastname,
                releaseYear: currentAlbum.releaseYear,
                contentDesc: currentAlbum.contentDesc
            }
        });
    };

    const renderAlbumList = () => {
        if(!loaded)
            return <CircularProgress color="inherit" />
        else if(!albums)
            return <div className="w-100 text-center">An error occurred while getting the albums</div>;
        else if(albums.length === 0)
            return (
                <React.Fragment>
                    <div className="w-100 text-center">There are no albums yet</div>
                    <Fab className="mt-3 mb-3" size="medium" onClick={addRedirect}>
                        <AddIcon />
                    </Fab>
                </React.Fragment>
            );

        return (
            <React.Fragment>
                <div className="albums">
                    {albums.map(album => (
                        <div key={album.isrc} id={"ISRC" + album.isrc} className="album" onClick={(e) => toggleDetails(e, album)}>
                            <AlbumSummary title={album.title} firstname={album.artist.firstname} lastname={album.artist.lastname} />
                        </div>
                    ))}
                </div>

                <Fab className="mt-3 mb-3" size="medium" onClick={addRedirect}>
                    <AddIcon />
                </Fab>
            </React.Fragment>
        );
    };

    const renderAlbumDetails = () => {
        if(currentAlbum)
            return <AlbumDetails title={currentAlbum.title} firstname={currentAlbum.artist.firstname} lastname={currentAlbum.artist.lastname}
                             isrc={currentAlbum.isrc} releaseYear={currentAlbum.releaseYear} contentDesc={currentAlbum.contentDesc}
                             handleClose={closeDetails} handleEdit={editRedirect} />;
    };

    return (
        <React.Fragment>
            <Navbar />

            <div id="albumList">
                <h3>Albums</h3>

                {renderAlbumList()}

                <Slide direction="up" in={currentAlbum != null} mountOnEnter unmountOnExit>
                    <div className="albumDetails">
                        {renderAlbumDetails()}
                    </div>
                </Slide>
            </div>
        </React.Fragment>
    );
}

export default AlbumList;
