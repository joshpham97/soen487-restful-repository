import React from 'react';
import { albumApi, albumServer } from '../../endpoints/albumServer';
import { FormControl, Button } from '@material-ui/core';
import {useEffect, useState} from 'react';
import SaveIcon from '@material-ui/icons/Save';
import DeleteIcon from '@material-ui/icons/Delete';
import { formatter } from '../../utilities/formatter';
import Alert from '@material-ui/lab/Alert';


function AlbumCoverForm(props) {
    const [coverImageFile, setCoverImageFile] = useState(false);

    const imageNotFound = () => {
        document.getElementById('coverImage').hidden = "hidden";
        document.getElementById('noImageAlert').removeAttribute("hidden");
    }

    const renderCoverImage = () => {
        return <div>
                <Alert id="noImageAlert" severity="error" hidden>This album has no cover image</Alert>
                <img id="coverImage" src={formatter.getCoverImageUrl(props.isrc)} onError={imageNotFound} style={{"width" : "30%"}}/>
            </div>        
    }

    const updateCoverImage = () => {
        const url = albumApi.addAlbumCover + "/" + props.isrc;

        const formData = new FormData();
        formData.append('file', coverImageFile)
        const config = {
            headers: {
                'content-type': 'multipart/form-data'
            }
        }
    
        albumServer.put(url, formData, config)
        .then(res => {
            reloadImage();
            alert("Updated the album with new cover image");
        })
        .catch(err => {
            if(err.response){
                alert(err.response.data);
            }
        });
    };

    const deleteCoverImage = () => {
        const url = albumApi.deleteAlbumCover + "/" + props.isrc;

        albumServer.delete(url)
            .then(() => {
                reloadImage();
                alert("Cover image deleted");
            })
            .catch(err => {
                if(err.response){
                    alert(err.response.data);
                }
            });
    };

    const reloadImage = () => {
        document.getElementById('noImageAlert').hidden = "hidden";
        document.getElementById('coverImage').removeAttribute("hidden");
        document.getElementById('coverImage').src = formatter.getCoverImageUrl(props.isrc);
    }

    const renderButtons = () => {
        return (
            <React.Fragment>
                <Button className="mr-3" variant="contained" color="primary" startIcon={<SaveIcon />} onClick={updateCoverImage}>Save</Button>
                <Button variant="contained" color="secondary" startIcon={<DeleteIcon />} onClick={deleteCoverImage}>Delete</Button>
            </React.Fragment>
        );
    };

    return (
        <div id="albumCoverForm">
            <h5>Album cover (optional)</h5> 

            <div className="formRow">
                <FormControl className="formColumn">
                    {renderCoverImage()}
                    <div style={{"marginTop": "10px"}}>
                        <input type="file" onChange={(e) => setCoverImageFile(e.currentTarget.files[0])}/>
                    </div>
                </FormControl>
            </div>

            {renderButtons()}
        </div>
    );
}

export default AlbumCoverForm;
