import '../styles/albumDetails.css';

import React from 'react';
import { useState, useEffect, useRef } from 'react';
import { useHistory, useParams, useLocation } from 'react-router';
import { withStyles } from "@material-ui/core/styles";
import { CircularProgress, Divider } from "@material-ui/core";
import MuiArrowBackIosRoundedIcon from '@material-ui/icons/ArrowBackIosRounded';

import Navbar from './Navbar';
import AlbumCover from './subcomponents/AlbumCover';
import { albumApi, albumServer } from "../endpoints/albumServer";

const ArrowBackIosRoundedIcon = withStyles({
    root: {
        position: 'absolute',
        left: '2pc',
        cursor: 'pointer'
    }
})(MuiArrowBackIosRoundedIcon);

function AlbumDetails() {
    const history = useHistory();
    const location = useLocation();

    const [loaded, setLoaded] = useState(false);
    const [album ,setAlbum] = useState(null);
    const isrcRef = useRef(null);
    const { isrc } = useParams();

    useEffect( () => {
        // Mount
        if(isrc)
            isrcRef.current = isrc;
        else
            isrcRef.current = location.state.isrc;

        getAlbum();
        const interval = setInterval(getAlbum, 1000);

        // Unmount
        return () => clearInterval(interval);
    }, [location.state, isrc]);

    const getAlbum = () => {
        albumServer.get(albumApi.get + '/' + isrcRef.current)
            .then(res => {
                if(res.data.isrc)
                    setAlbum(res.data)
            })
            .catch(err => console.log(err))
            .finally(() => setLoaded(true));
    };

    const backRedirect = () => {
        let state = location.state ? location.state : {};
        state.album = album ? album : null;

        history.push({
            pathname: '/albums',
            state: state
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

                <div className="bottomSection">
                    <div className="releaseYear">
                        Released in {album.releaseYear}
                    </div>

                    <div className="description">
                        <div className="descHeader">Description</div>
                        <div className="descContent">{album.contentDesc}</div>
                    </div>
                </div>
            </React.Fragment>
        );
    };

    return (
        <React.Fragment>
            <Navbar />

            <div id="albumDetails">
                <ArrowBackIosRoundedIcon fontSize="large" onClick={backRedirect} />

                <h3>
                    Album
                    <div className="isrc">#{album ? album.isrc : isrc}</div>
                </h3>

                {renderAlbum()}
            </div>
        </React.Fragment>
    );
}

export default AlbumDetails;