import React, {useRef} from "react";
import { useState, useEffect } from 'react';
import { useHistory, useLocation } from 'react-router';
import { InputLabel, Input, FormControl, Button, TextField } from '@material-ui/core';
import ArrowBackIosRoundedIcon from '@material-ui/icons/ArrowBackIosRounded';
import FilterListIcon from '@material-ui/icons/FilterList';
import RotateLeftIcon from '@material-ui/icons/RotateLeft';

import Navbar from './subcomponents/Navbar';

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
                <ArrowBackIosRoundedIcon fontSize="large" className="back" onClick={backRedirect} />

                <h3>
                    Filter Album
                </h3>

                <div className="formRow">
                    <FormControl className="formColumn">
                        <InputLabel htmlFor="albumTitleInput">Title</InputLabel>
                        <Input id="albumTitleInput" value={title}
                               onChange={(e) => setTitle(e.currentTarget.value)}/>
                    </FormControl>

                    <FormControl className="formColumn">
                        <InputLabel htmlFor="albumNameInput">Name</InputLabel>
                        <Input id="albumNameInput" value={name}
                               onChange={(e) => setName(e.currentTarget.value)}/>
                    </FormControl>
                </div>

                <div className="formRow">
                    <TextField className="formColumn" type="number" label="From" variant="outlined" value={from}
                               error={fromError !== ''} helperText={fromError}
                               onChange={(e) => setFrom(e.currentTarget.value)} />

                    <TextField className="formColumn" type="number" label="To" variant="outlined" value={to}
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
