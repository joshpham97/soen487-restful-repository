import React, {useRef} from "react";
import { useState, useEffect } from 'react';
import { useHistory, useLocation } from 'react-router';
import { withStyles } from "@material-ui/core/styles";
import {InputLabel, Input, FormControl, Button, TextField} from '@material-ui/core';
import MuiArrowBackIosRoundedIcon from '@material-ui/icons/ArrowBackIosRounded';
import FilterListIcon from '@material-ui/icons/FilterList';
import RotateLeftIcon from '@material-ui/icons/RotateLeft';

import Navbar from './Navbar';

const ArrowBackIosRounded = withStyles({
    root: {
        position: 'absolute',
        left: '2pc',
        cursor: 'pointer'
    }
})(MuiArrowBackIosRoundedIcon);

function AlbumFilter() {
    const history = useHistory();
    const location = useLocation();

    const [title, setTitle] = useState('');
    const [name, setName] = useState('');
    const [from, setFrom] = useState('');
    const [to, setTo] = useState('');
    const yearRef = useRef(new Date().getFullYear());

    const [fromError, setFromError] = useState('');
    const [toError, setToError] = useState('');

    useEffect( () => {
        // Mount
        const params = location.state;
        if(params && params.filter) {
            if(params.filter.title)
                setTitle(params.filter.title);
            if(params.filter.name)
                setName(params.filter.name);
            if(params.filter.from)
                setFrom(params.filter.from);
            if(params.filter.to)
                setTo(params.filter.to);
        }
    }, [location.state]);

    const backRedirect = () => {
        history.push({
            pathname: '/albums',
            state: location.state
        });
    };

    const filterAlbums = () => {
        let valid = true;

        if(from && (from <= 0 || to > yearRef.current)) {
            setFromError('Invalid value');
            valid = false;
        }


        if(to && (to <= 0 || to > yearRef.current)) {
            setToError('Invalid value');
            valid = false;
        }

        if(valid) {
            history.push({
                pathname: '/albums',
                state: {
                    filter: {
                        title: title,
                        name: name,
                        from: from,
                        to: to
                    }
                }
            });
        }
    };

    const albumsRedirect = () => {
        history.push('/albums');
    };

    return (
        <React.Fragment>
            <Navbar />

            <div id="albumForm">
                <ArrowBackIosRounded fontSize="large" onClick={backRedirect} />

                <h3>
                    Filter Album
                </h3>

                <div className="formRow">
                    <FormControl className="mr-5">
                        <InputLabel htmlFor="albumTitleInput">Title</InputLabel>
                        <Input id="albumTitleInput" value={title}
                               onChange={(e) => setTitle(e.currentTarget.value)}/>
                    </FormControl>

                    <FormControl>
                        <InputLabel htmlFor="albumNameInput">Name</InputLabel>
                        <Input id="albumNameInput" value={name}
                               onChange={(e) => setName(e.currentTarget.value)}/>
                    </FormControl>
                </div>

                <div className="formRow">
                    <TextField className="mr-5" type="number" label="From" variant="outlined" value={from}
                               error={fromError !== ''} helperText={fromError}
                               onChange={(e) => setFrom(e.currentTarget.value)} />

                    <TextField type="number" label="To" variant="outlined" value={to}
                               error={toError !== ''} helperText={toError}
                               onChange={(e) => setTo(e.currentTarget.value)} />
                </div>

                <Button className="mr-3" variant="contained" color="primary" startIcon={<FilterListIcon />} onClick={filterAlbums}>Filter</Button>
                <Button variant="contained" color="secondary" startIcon={<RotateLeftIcon />} onClick={albumsRedirect}>Reset</Button>
            </div>
        </React.Fragment>
    );
}

export default AlbumFilter;