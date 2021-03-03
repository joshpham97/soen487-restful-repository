import React from "react";
import { useState, useEffect } from 'react';
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

function AlbumFilter(props) {
    const [title, setTitle] = useState('');
    const [name, setName] = useState('');
    const [from, setFrom] = useState('');
    const [to, setTo] = useState('');

    useEffect( () => {
        // Mount
        const params = props.location.state;
        if(params) {
            if(params.titleFilter)
                setTitle(params.titleFilter);
            if(params.nameFilter)
                setName(params.nameFilter);
            if(params.fromFilter)
                setFrom(params.fromFilter);
            if(params.toFilter)
                setTo(params.toFilter);
        }

    }, []);

    const handleInput = (e, setState) => {
        setState(e.currentTarget.value);
    };

    const backRedirect = () => {
        props.history.push({
            pathname: '/albums',
            state: props.location.state
        });
    };

    const filterAlbums = () => {
        // let params = new URLSearchParams();

        // if(title)
        //     params.append('title', title);
        //
        // if(name)
        //     params.append('name', name);
        //
        // if(from)
        //     params.append('from', from);
        //
        // if(to)
        //     params.append('to', to);

        props.history.push({
            pathname: '/albums',
            state: {
                titleFilter: title,
                nameFilter: name,
                fromFilter: from,
                toFilter: to
            }
            // search: '?' + params.toString()
        });
    };

    const albumsRedirect = () => {
        props.history.push('/albums');
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
                        <Input id="albumTitleInput" value={title} onChange={(e) => handleInput(e, setTitle)}/>
                    </FormControl>

                    <FormControl>
                        <InputLabel htmlFor="albumNameInput">Name</InputLabel>
                        <Input id="albumNameInput" value={name} onChange={(e) => handleInput(e, setName)}/>
                    </FormControl>
                </div>

                <div className="formRow">
                    <TextField className="mr-5" type="number" label="From" variant="outlined" value={from} onChange={(e) => handleInput(e, setFrom)} />

                    <TextField type="number" label="To" variant="outlined" value={to} onChange={(e) => handleInput(e, setTo)} />

                </div>

                <Button className="mr-3" variant="contained" color="primary" startIcon={<FilterListIcon />} onClick={filterAlbums}>Filter</Button>
                <Button variant="contained" color="secondary" startIcon={<RotateLeftIcon />} onClick={albumsRedirect}>Reset</Button>
            </div>
        </React.Fragment>
    );
}

export default AlbumFilter;
