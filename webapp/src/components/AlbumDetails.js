import '../styles/albumDetails.css';

import React from 'react';
import { useState, useEffect } from 'react';
import { useParams } from 'react-router';
import { withStyles } from "@material-ui/core/styles";
import { CircularProgress, Divider } from "@material-ui/core";
import MuiArrowBackIosRoundedIcon from '@material-ui/icons/ArrowBackIosRounded';

import Navbar from './Navbar';
import AlbumCover from './subcomponents/AlbumCover';
import { albumApi, albumServer } from "../endpoints/albumServer";

const ArrowBackIosRounded = withStyles({
    root: {
        position: 'absolute',
        left: '2pc',
        cursor: 'pointer'
    }
})(MuiArrowBackIosRoundedIcon);

function AlbumDetails(props) {
    const [loaded, setLoaded] = useState(false);
    const [album ,setAlbum] = useState(null);
    let {isrc} = useParams();

    useEffect( () => {
        // Mount
        if(!isrc)
            isrc = props.location.state.isrc;

        getAlbum();
        const interval = setInterval(getAlbum, 1000);

        // Unmount
        return () => clearInterval(interval);
    }, []);

    const getAlbum = () => {
        albumServer.get(albumApi.get + '/' + isrc)
            .then(res => {
                if(res.data.isrc)
                    setAlbum(res.data)
            })
            .catch(err => console.log(err))
            .finally(() => setLoaded(true));
    };

    const backRedirect = () => {
        const propIsrc = album ? isrc : null;

        props.history.push({
            pathname: '/albums',
            state: {
                isrc: propIsrc
            }
        });
    };

    const renderAlbum = () => {
        if(!loaded)
            return <CircularProgress color="inherit"/>;
        else if(!album)
            return <div>This album doesn't exist</div>

        return (
            <React.Fragment>
                <div className="mt-3 albumCover">
                    <AlbumCover title={album.title} firstname={album.artist.firstname} lastname={album.artist.lastname} />
                </div>

                <Divider className="mt-3" />

                <div className="releaseYear">
                    Released in {album.releaseYear}
                </div>

                <div className="description">
                    <div className="descHeader">Description</div>
                    <div className="descContent">{album.contentDesc}</div>
                </div>
            </React.Fragment>
        );
    };

    return (
        <React.Fragment>
            <Navbar />

            <ArrowBackIosRounded fontSize="large" onClick={backRedirect} />

            <div id="albumDetails">
                <h3>
                    Album
                    <div className="isrc">#{isrc}</div>
                </h3>

                {renderAlbum()}
            </div>
        </React.Fragment>
    );
}

export default AlbumDetails;