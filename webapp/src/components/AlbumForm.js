import '../styles/albumForm.css';

import React from 'react';
import { useState, useEffect } from 'react';
import { withStyles } from '@material-ui/core/styles';
import MuiTextField from '@material-ui/core/TextField';
import { FormControl, InputLabel, Input } from '@material-ui/core';

import Navbar from './Navbar';

const TextField = withStyles({
    root: {
        width: '50%',
        maxWidth: '400px',
        minWidth: '200px'
    }
})(MuiTextField);

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

    return (
        <React.Fragment>
            <Navbar />

            <div id="albumForm">
                <h3>Album</h3>

                <form>
                    <div className="formRow">
                        <FormControl className="mr-5">
                            <InputLabel htmlFor="albumTitleInput">Title</InputLabel>
                            <Input id="albumTitleInput" value={title} />
                        </FormControl>

                        <FormControl>
                            <InputLabel htmlFor="albumReleaseYearInput">Release Year</InputLabel>
                            <Input id="albumReleaseYearInput" value={releaseYear} type="number" />
                        </FormControl>
                    </div>

                    <div className="formRow">
                        <FormControl className="mr-5">
                            <InputLabel htmlFor="albumFirstNameInput">First Name</InputLabel>
                            <Input id="albumFirstNameInput" value={firstname} />
                        </FormControl>

                        <FormControl>
                            <InputLabel htmlFor="albumLastNameInput">Last Name</InputLabel>
                            <Input id="albumLastNameInput" value={lastname} />
                        </FormControl>
                    </div>

                    <div className="formRow">
                        <TextField id="albumContentDescInput" value={contentDesc} label="Description"
                                   variant="outlined" rows={5} multiline />
                    </div>
                </form>
            </div>
        </React.Fragment>
    );
}

export default AlbumForm;