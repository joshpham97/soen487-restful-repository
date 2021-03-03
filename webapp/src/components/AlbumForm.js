import '../styles/albumForm.css';

import React from 'react';
import { useState, useEffect } from 'react';
import { withStyles } from '@material-ui/core/styles';
import MuiTextField from '@material-ui/core/TextField';
import { FormControl, InputLabel, Input, TextField, Button } from '@material-ui/core';
import MuiArrowBackIosRoundedIcon from '@material-ui/icons/ArrowBackIosRounded';
import AddIcon from '@material-ui/icons/Add';
import SaveIcon from '@material-ui/icons/Save';
import DeleteIcon from '@material-ui/icons/Delete';

import Navbar from './Navbar';
import { albumApi, albumServer } from "../endpoints/albumServer";

const DescInput = withStyles({
    root: {
        width: '50%',
        maxWidth: '400px',
        minWidth: '200px'
    }
})(MuiTextField);

const ArrowBackIosRounded = withStyles({
    root: {
        position: 'absolute',
        left: '2pc',
        cursor: 'pointer'
    }
})(MuiArrowBackIosRoundedIcon);

function AlbumForm(props) {
    const [isrc, setIsrc] = useState('');
    const [title, setTitle] = useState('');
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [releaseYear, setReleaseYear] = useState('');
    const [contentDesc, setContentDesc] = useState('');
    const params = props.location.state;

    useEffect(() => {
        // Mount
        if(params && params.isrc) {
            setTitle(params.title);
            setFirstname(params.firstname);
            setLastname(params.lastname);
            setReleaseYear(params.releaseYear);
            setContentDesc(params.contentDesc);
        }
    }, []);

    const handleInput = (e, setState) => {
        setState(e.target.value);
    };

    const addAlbum = () => {
        albumServer.post(albumApi.add, {
            isrc: isrc,
            title: title,
            releaseYear: releaseYear,
            contentDesc: contentDesc,
            artist: {
                firstname: firstname,
                lastname: lastname
            }
        })
            .then(res => props.history.push({
                pathname: '/albums',
                state: {
                    isrc: isrc
                }
            }))
            .catch(err => alert(err));
    };

    const updateAlbum = () => {
        albumServer.put(albumApi.update, {
            isrc: params.isrc,
            title: title,
            releaseYear: releaseYear,
            contentDesc: contentDesc,
            artist: {
                firstname: firstname,
                lastname: lastname
            }
        })
            .then(res => props.history.push({
                pathname: '/albums',
                state: {
                    isrc: params.isrc
                }
            }))
            .catch(err => console.log(err));
    };

    const deleteAlbum = () => {
        albumServer.delete(albumApi.delete + '/' + params.isrc)
            .then(res => props.history.push('/albums'))
            .catch(err => alert(err));
    };

    const backRedirect = () => {
        let state = props.location.state ? props.location.state : {};
        console.log(state);
        state.isrc = params && params.isrc ? params.isrc : '';

        props.history.push({
            pathname: '/albums',
            state: state
        });
    };

    const renderHeader = () => {
        if(params && params.isrc) {
            return (
                <React.Fragment>
                    Edit Album
                    <div className="isrc">#{params.isrc}</div>
                </React.Fragment>
            );
        }

        return "Add Album";
    };

    const renderIsrcInput = () => {
        if(!params || !params.isrc)
            return (
                <div className="formRow">
                    <TextField label="ISRC" variant="outlined" value={isrc} onChange={(e) => handleInput(e, setIsrc)} />
                </div>
            );
    };

    const renderButtons = () => {
        if(params && params.isrc)
            return (
                <React.Fragment>
                    <Button className="mr-3" variant="contained" color="primary" startIcon={<SaveIcon />} onClick={updateAlbum}>Save</Button>
                    <Button variant="contained" color="secondary" startIcon={<DeleteIcon />} onClick={deleteAlbum}>Delete</Button>
                </React.Fragment>
            );

        return <Button variant="contained" color="primary" startIcon={<AddIcon />} onClick={addAlbum}>Add</Button>
    };

    return (
        <React.Fragment>
            <Navbar />

            <div id="albumForm">
                <ArrowBackIosRounded fontSize="large" onClick={backRedirect} />

                <h3>
                    {renderHeader()}
                </h3>

                {renderIsrcInput()}

                <div className="formRow">
                    <FormControl className="mr-5">
                        <InputLabel htmlFor="albumTitleInput">Title</InputLabel>
                        <Input id="albumTitleInput" value={title}
                               onChange={(e) => handleInput(e, setTitle)} />
                    </FormControl>

                    <FormControl>
                        <InputLabel htmlFor="albumReleaseYearInput">Release Year</InputLabel>
                        <Input id="albumReleaseYearInput" value={releaseYear} type="number"
                               onChange={(e) => handleInput(e, setReleaseYear)} />
                    </FormControl>
                </div>

                <div className="formRow">
                    <FormControl className="mr-5">
                        <InputLabel htmlFor="albumFirstNameInput">First Name</InputLabel>
                        <Input id="albumFirstNameInput" value={firstname}
                               onChange={(e) => handleInput(e, setFirstname)} />
                    </FormControl>

                    <FormControl>
                        <InputLabel htmlFor="albumLastNameInput">Last Name</InputLabel>
                        <Input id="albumLastNameInput" value={lastname}
                               onChange={(e) => handleInput(e, setLastname)} />
                    </FormControl>
                </div>

                <div className="formRow">
                    <DescInput id="albumContentDescInput" value={contentDesc} label="Description"
                               variant="outlined" rows={5} multiline
                               onChange={(e) => handleInput(e, setContentDesc)} />
                </div>

                {renderButtons()}
            </div>
        </React.Fragment>
    );
}

export default AlbumForm;