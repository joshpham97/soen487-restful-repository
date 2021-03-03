import '../styles/albumList.css';

import React from 'react';
import { useState, useEffect } from 'react';
import { withStyles } from '@material-ui/core/styles';
import { CircularProgress, Fab, Slide } from '@material-ui/core';
import MuiAddIcon from '@material-ui/icons/Add';
import FilterListIcon from '@material-ui/icons/FilterList';

import Navbar from './Navbar';
import AlbumCover from './subcomponents/AlbumCover';
import AlbumSummary from './subcomponents/AlbumSummary';
import { albumApi, albumServer } from "../endpoints/albumServer";

const AddIcon = withStyles({
    root: {
        cursor: 'pointer'
    }
})(MuiAddIcon);

function AlbumList(props) {
    const [loaded, setLoaded] = useState(false);
    const [currentAlbum, setCurrentAlbum] = useState(null);
    const [albums, setAlbums] = useState([]);

    let { titleFilter, nameFilter, fromFilter, toFilter } = '';

    useEffect( () => {
        // Mount
        const params = props.location.state;
        if(params) {
            if (params.isrc)
                getAlbum();

            if(params.titleFilter)
                titleFilter = params.titleFilter;
            if(params.nameFilter)
                nameFilter = params.nameFilter;
            if(params.fromFilter)
                fromFilter = params.fromFilter;
            if(params.toFilter)
                toFilter = params.toFilter;
        }

        getAlbumList();
        const interval = setInterval(getAlbumList, 1000);

        // Unmount
        return () => clearInterval(interval);
    }, []);

    const getAlbumList = () => {
        albumServer.get(albumApi.albums, {
            params: {
                title: titleFilter,
                fromYear: fromFilter,
                toYear: toFilter,
                name: nameFilter
            }
        })
            .then(res => setAlbums(res.data))
            .catch(err => {
                console.log(err);
                setAlbums(null);
            })
            .finally(() => setLoaded(true));
    };

    const getAlbum = () => {
        albumServer.get(albumApi.get + '/' + props.location.state.isrc)
            .then(res => setCurrentAlbum(res.data))
            .catch(err => console.log(err));
    };

    const toggleSummary = (e, album) => {
        // Set current album to trigger details
        if(currentAlbum && currentAlbum.isrc === album.isrc)
            setCurrentAlbum(null);
        else
            setCurrentAlbum(album);
    };

    const closeSummary = () => {
        setCurrentAlbum(null);
    };

    const horizontalScroll = (e) => {
        const delta = Math.max(-1, Math.min(1, (e.nativeEvent.wheelDelta || -e.nativeEvent.detail)));
        e.currentTarget.scrollLeft -= (delta * 25);
    };

    const addRedirect = () => {
        let state = props.location.state ? props.location.state : {};
        state.isrc = null;

        props.history.push({
            pathname: '/albums/add',
            state: state
        });
    };

    const getRedirect = (isrc) => {
        let state = props.location.state ? props.location.state : {};
        state.isrc = isrc;

        props.history.push({
            pathname: '/albums/get',
            state: state
        });
    };

    const editRedirect = () => {
        props.history.push({
            pathname: '/albums/edit',
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

    const filterRedirect = () => {
        props.history.push({
            pathname: '/albums/filter',
            state: props.location.state
        });
    };

    const renderAlbumList = () => {
        if(!loaded)
            return <CircularProgress color="inherit" />
        else if(!albums)
            return <div className="w-100 text-center">An error occurred while getting the albums</div>;
        else if(albums.length === 0) {
            const params = props.location.state;
            if(params.titleFilter || params.nameFilter || params.fromFilter || params.toFilter)
                return (
                    <React.Fragment>
                        <div className="w-100 text-center">There are no matches</div>
                        <div className="mt-3 mb-3">
                            <Fab className="mr-3" size="medium" onClick={addRedirect}>
                                <AddIcon />
                            </Fab>

                            <Fab size="medium" onClick={filterRedirect}>
                                <FilterListIcon />
                            </Fab>
                        </div>
                    </React.Fragment>
                );

            return (
                <React.Fragment>
                    <div className="w-100 text-center">There are no albums yet</div>
                    <Fab className="mt-3 mb-3" size="medium" onClick={addRedirect}>
                        <AddIcon/>
                    </Fab>
                </React.Fragment>
            );
        }

        return (
            <React.Fragment>
                <div className="albums" onWheel={(e) => horizontalScroll(e)}>
                    {albums.map(album => (
                        <div key={album.isrc} id={"ISRC" + album.isrc} className="album" onClick={(e) => toggleSummary(e, album)}>
                            <AlbumCover title={album.title} firstname={album.artist.firstname} lastname={album.artist.lastname} />
                        </div>
                    ))}
                </div>

                <div className="mt-3 mb-3">
                    <Fab className="mr-3" size="medium" onClick={addRedirect}>
                        <AddIcon />
                    </Fab>

                    <Fab size="medium" onClick={filterRedirect} >
                        <FilterListIcon />
                    </Fab>
                </div>
            </React.Fragment>
        );
    };

    const renderAlbumSummary = () => {
        if(currentAlbum)
            return <AlbumSummary title={currentAlbum.title} firstname={currentAlbum.artist.firstname} lastname={currentAlbum.artist.lastname}
                                 isrc={currentAlbum.isrc} releaseYear={currentAlbum.releaseYear} contentDesc={currentAlbum.contentDesc}
                                 handleClose={closeSummary} handleOpenInBrowser={() => getRedirect(currentAlbum.isrc)} handleEdit={editRedirect} />;
    };

    return (
        <React.Fragment>
            <Navbar />

            <div id="albumList">
                <h3>Albums</h3>

                {renderAlbumList()}

                <Slide direction="up" in={currentAlbum != null} mountOnEnter unmountOnExit>
                    <div className="albumSummary">
                        {renderAlbumSummary()}
                    </div>
                </Slide>
            </div>
        </React.Fragment>
    );
}

export default AlbumList;
