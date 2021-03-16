import '../styles/albumForm.css';

import React from 'react';
import { useState, useEffect, useRef } from 'react';
import { useHistory, useLocation } from 'react-router';
import { withStyles } from '@material-ui/core/styles';
import MuiTextField from '@material-ui/core/TextField';
import { FormControl, TextField, Button } from '@material-ui/core';
import ArrowBackIosRoundedIcon from '@material-ui/icons/ArrowBackIosRounded';
import AddIcon from '@material-ui/icons/Add';
import SaveIcon from '@material-ui/icons/Save';
import DeleteIcon from '@material-ui/icons/Delete';
import Divider from '@material-ui/core/Divider';

import Navbar from './subcomponents/Navbar';
import { albumApi, albumServer } from "../endpoints/albumServer"; 
import AlbumCoverForm from './subcomponents/AlbumCoverForm';

const DescInput = withStyles({
    root: {
        width: '50%',
        maxWidth: '400px',
        minWidth: '200px'
    }
})(MuiTextField);

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

    const [isrcError, setIsrcError] = useState('');
    const [titleError, setTitleError] = useState('');
    const [releaseYearError, setReleaseYearError] = useState('');
    const [firstnameError, setFirstnameError] = useState('');
    const [lastnameError, setLastnameError] = useState('');

    useEffect(() => {
        // Mount
        const params = location.state;
        if(params && params.isrc) {
            setIsrc(params.isrc);
            setTitle(params.title);
            setFirstname(params.firstname);
            setLastname(params.lastname);
            setReleaseYear(params.releaseYear);
            setContentDesc(params.contentDesc);
        }
    }, [location.state]);

    const submit = (operation) => {
        let valid = true;

        if((!location.state || !location.state.isrc) && !isrc) {
            setIsrcError('Required');
            valid = false;
        }

        if(!title || !title.trim()) {
            setTitleError('Required');
            valid = false;
        }

        if(!releaseYear) {
            setReleaseYearError('Required');
            valid = false;
        }
        else if(releaseYear < 1901 || releaseYear > yearRef.current) {
            setReleaseYearError('Accepted values: 1901 to ' + yearRef.current);
            valid = false;
        }

        if(!firstname || !firstname.trim()) {
            setFirstnameError('Required');
            valid = false;
        }

        if(!lastname || !lastname.trim()) {
            setLastnameError('Required');
            valid = false;
        }

        if(valid)
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
            .then(res => {
                history.push({
                    pathname: '/albums',
                    state: {
                        album: res.data
                    }
                });
            })
            .catch(err => {
                if(err.response)
                    alert(err.response.data);
            });
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
            .catch(err => {
                if(err.response)
                    alert(err.response.data);
            });
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
            .catch(err => {
                if(err.response)
                    alert(err.response.data);
            });
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
                    <TextField label="ISRC" variant="outlined" value={isrc} error={isrcError !== ''} helperText={isrcError}
                               onChange={(e) => setIsrc(e.currentTarget.value)} />
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

    const renderCoverImageForm = () => {
        if(location.state && location.state.isrc)
            return (
                <React.Fragment>
                    <Divider variant="middle" style={{"margin": "20px auto 20px auto", "width": "60%"}} />
                    <AlbumCoverForm isrc={isrc}/>
                </React.Fragment>
            );
    };

    return (
        <React.Fragment>
            <Navbar />

            <div id="albumForm">
                <ArrowBackIosRoundedIcon className="back" fontSize="large" onClick={backRedirect} />

                <h3>
                    {renderHeader()}
                </h3>

                {renderIsrcInput()}

                <div className="formRow">
                    <FormControl className="formColumn">
                        <TextField label="Title" value={title} error={titleError !== ''} helperText={titleError}
                                   onChange={(e) => setTitle(e.currentTarget.value)} />
                    </FormControl>

                    <FormControl className="formColumn">
                        <TextField type="number" label="Release Year" value={releaseYear} error={releaseYearError !== ''} helperText={releaseYearError}
                                   onChange={(e) => setReleaseYear(e.currentTarget.value)} />
                    </FormControl>
                </div>

                <div className="formRow">
                    <FormControl className="formColumn">
                        <TextField label="First Name" value={firstname} error={firstnameError !== ''} helperText={firstnameError}
                                   onChange={(e) => setFirstname(e.currentTarget.value)} />
                    </FormControl>

                    <FormControl className="formColumn">
                        <TextField label="Last Name" value={lastname} error={lastnameError !== ''} helperText={lastnameError}
                                   onChange={(e) => setLastname(e.currentTarget.value)} />
                    </FormControl>
                </div>

                <div className="formRow">
                    <DescInput id="albumContentDescInput" value={contentDesc} label="Description" variant="outlined" rows={5} multiline
                               onChange={(e) => setContentDesc(e.currentTarget.value)} />
                </div>

                {renderButtons()}

                {renderCoverImageForm()}
            </div>
        </React.Fragment>
    );
}

export default AlbumForm;