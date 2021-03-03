import '../styles/albumForm.css';

import React from 'react';
import { useState, useEffect, useRef } from 'react';
import { useHistory, useLocation } from 'react-router';
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

function AlbumForm() {
    const history = useHistory();
    const location = useLocation();

    const [isrc, setIsrc] = useState('');
    const [title, setTitle] = useState('');
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [releaseYear, setReleaseYear] = useState('');
    const [contentDesc, setContentDesc] = useState('');
    const yearRef = useRef(new Date().getFullYear());

    const [releaseYearError, setReleaseYearError] = useState(false);

    useEffect(() => {
        // Mount
        const params = location.state;
        if(params && params.isrc) {
            setTitle(params.title);
            setFirstname(params.firstname);
            setLastname(params.lastname);
            setReleaseYear(params.releaseYear);
            setContentDesc(params.contentDesc);
        }
    }, [location.state]);

    const handleInput = (e, setState) => {
        setState(e.target.value);
    };

    const submit = (operation) => {
        if(releaseYear < 1901 || releaseYear > yearRef.current) {
            setReleaseYearError(true);
            return;
        }

        operation();
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
            .then(res => history.push({
                pathname: '/albums',
                state: {
                    album: res.data
                }
            }))
            .catch(err => alert(err));
    };

    const updateAlbum = () => {
        albumServer.put(albumApi.update, {
            isrc: location.state.isrc,
            title: title,
            releaseYear: releaseYear,
            contentDesc: contentDesc,
            artist: {
                firstname: firstname,
                lastname: lastname
            }
        })
            .then(res => history.push({
                pathname: '/albums',
                state: {
                    album: res.data
                }
            }))
            .catch(err => alert(err));
    };

    const deleteAlbum = () => {
        albumServer.delete(albumApi.delete + '/' + location.state.isrc)
            .then(() => {
                location.state.album = null;

                history.push({
                    pathname: '/albums',
                    state: location.state
                });
            })
            .catch(err => alert(err));
    };

    const backRedirect = () => {
        let state = location.state ? location.state : {};
        state.album = location.state && location.state.album ? location.state.album : null;

        history.push({
            pathname: '/albums',
            state: state
        });
    };

    const renderHeader = () => {
        if(location.state && location.state.isrc) {
            return (
                <React.Fragment>
                    Edit Album
                    <div className="isrc">#{location.state.isrc}</div>
                </React.Fragment>
            );
        }

        return "Add Album";
    };

    const renderIsrcInput = () => {
        if(!location.state || !location.state.isrc)
            return (
                <div className="formRow">
                    <TextField label="ISRC" variant="outlined" value={isrc} onChange={(e) => handleInput(e, setIsrc)} />
                </div>
            );
    };

    const renderButtons = () => {
        if(location.state && location.state.isrc)
            return (
                <React.Fragment>
                    <Button className="mr-3" variant="contained" color="primary" startIcon={<SaveIcon />} onClick={() => submit(updateAlbum)}>Save</Button>
                    <Button variant="contained" color="secondary" startIcon={<DeleteIcon />} onClick={deleteAlbum}>Delete</Button>
                </React.Fragment>
            );

        return <Button variant="contained" color="primary" startIcon={<AddIcon />} onClick={() => submit(addAlbum)}>Add</Button>
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
                        <TextField type="number" label="Release Year" value={releaseYear} error={releaseYearError}
                                   helperText={releaseYearError ? "Accepted values: 1901 to " + yearRef.current : ""}
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