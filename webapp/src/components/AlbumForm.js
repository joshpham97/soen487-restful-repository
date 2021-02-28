import '../styles/albumForm.css';

import React from 'react';
import { useState, useEffect } from 'react';
import { withStyles } from '@material-ui/core/styles';
import MuiTextField from '@material-ui/core/TextField';
import { FormControl, InputLabel, Input, Button } from '@material-ui/core';
import MuiArrowBackIosRoundedIcon from '@material-ui/icons/ArrowBackIosRounded';
import SaveIcon from '@material-ui/icons/Save';
import DeleteIcon from '@material-ui/icons/Delete';

import Navbar from './Navbar';
import {Delete} from "@material-ui/icons";

const TextField = withStyles({
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
    const [title, setTitle] = useState('');
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [releaseYear, setReleaseYear] = useState('');
    const [contentDesc, setContentDesc] = useState('');

    useEffect(() => {
        // Mount
        setTitle(props.location.state.title);
        setFirstname(props.location.state.firstname);
        setLastname(props.location.state.lastname);
        setReleaseYear(props.location.state.releaseYear);
        setContentDesc(props.location.state.contentDesc);
    }, []);

    const handleInput = (e, setState) => {
        setState(e.target.value);
    };

    const handleBack = () => {
        props.history.push('/album');
    };

    return (
        <React.Fragment>
            <Navbar />

            <div id="albumForm">
                <ArrowBackIosRounded fontSize="large" onClick={handleBack} />

                <h3>
                    Album
                    <span className="isrc">#{props.location.state.isrc}</span>
                </h3>

                <form>
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
                        <TextField id="albumContentDescInput" value={contentDesc} label="Description"
                                   variant="outlined" rows={5} multiline
                                   onChange={(e) => handleInput(e, setContentDesc)} />
                    </div>
                </form>

                <div>
                    <Button className="mr-3" variant="contained" color="primary" startIcon={<SaveIcon />}>Save</Button>
                    <Button variant="contained" color="secondary" startIcon={<DeleteIcon />}>Delete</Button>
                </div>
            </div>
        </React.Fragment>
    );
}

export default AlbumForm;