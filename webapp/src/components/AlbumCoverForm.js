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
import { formatter } from '../utilities/formatter';

const DescInput = withStyles({
    root: {
        width: '50%',
        maxWidth: '400px',
        minWidth: '200px'
    }
})(MuiTextField);

function AlbumCoverForm() {
    const history = useHistory();
    const location = useLocation();

    const [isrc, setIsrc] = useState('');
    const [title, setTitle] = useState('');
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [releaseYear, setReleaseYear] = useState('');
    const [contentDesc, setContentDesc] = useState('');
    const [coverImage, setCoverImage] = useState('')
    const [coverImageFile, setCoverImageFile] = useState(null)
    const yearRef = useRef(new Date().getFullYear());

    const [isrcError, setIsrcError] = useState('');
    const [titleError, setTitleError] = useState('');
    const [releaseYearError, setReleaseYearError] = useState('');
    const [firstnameError, setFirstnameError] = useState('');
    const [lastnameError, setLastnameError] = useState('');


    useEffect(() => {
        // Mount
        const params = location.state.album;
        if(params && params.isrc) {
            setIsrc(params.isrc);
        }

        console.log("useEffect() isrc is :" + params.isrc);

        //Check if the image exist
        /*albumServer.get(albumApi.getAlbumCover, {
            isrc: isrc
        }).then(res => {
            console.log("Get successful");
            setCoverImage(formatter.getCoverImageUrl(isrc));
        })*/

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

    const updateCoverImage = () => {
        console.log("Update cover image fired ");
        albumServer.put(albumApi.updateCoverImage, {
            isrc: isrc,
            file: coverImageFile
        })
        .then(res => alert("Update cover image success"))
        .catch(err => alert(err));
    };

    const deleteCoverImage = () => {
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
                    <TextField label="ISRC" variant="outlined" value={isrc} error={isrcError !== ''} helperText={isrcError}
                               onChange={(e) => setIsrc(e.currentTarget.value)} />
                </div>
            );
    };

    const renderButtons = () => {
        if(location.state && location.state.isrc)
            return (
                <React.Fragment>
                    <Button className="mr-3" variant="contained" color="primary" startIcon={<SaveIcon />} onClick={updateCoverImage}>Save</Button>
                    <Button variant="contained" color="secondary" startIcon={<DeleteIcon />} onClick={deleteCoverImage}>Delete</Button>
                </React.Fragment>
            );

        return <Button variant="contained" color="primary" startIcon={<AddIcon />} onClick={() => submit(updateCoverImage)}>Add</Button>
    };

    return (
        <React.Fragment>
            <div id="albumCoverForm">
                <h5>Album cover (optional)</h5>

                <div className="formRow">
                    <FormControl className="formColumn">
                        <input type="file" onChange={(e) => setCoverImageFile(e.currentTarget.files[0])}/>
                    </FormControl>
                </div>

                <div className="formRow">
                    <img src={formatter.getCoverImageUrl(isrc)} style={{"width" : "30%"}}/>
                </div>

                {renderButtons()}
            </div>
        </React.Fragment>
    );
}

export default AlbumCoverForm;