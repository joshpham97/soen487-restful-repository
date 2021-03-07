import { Component } from 'react';
import { albumServer, albumApi } from '../endpoints/albumServer';
import Navbar from './subcomponents/Navbar';

class Example extends Component {
    constructor(props) {
        super(props);

        this.state = {
            albums: [],
            errorMessage: null
        };
    }

    componentDidMount() {
        this.getAlbums();
        this.interval = setInterval(this.getAlbums, 1000);
    }

    componentWillUnmount() {
        clearInterval(this.interval);
    }

    getAlbums = () => {
        albumServer.get(albumApi.albums)
            .then(res => {
                this.setState({
                   albums: res.data
                });
            })
            .catch(err => {
                console.log(err);
                this.setState({
                    albums: null
                });
            });
    }

    renderAlbums = () => {
        const albums = this.state.albums;

        if(!albums)
            return <div>An error occurred while getting the albums</div>;
        else if(albums.length === 0)
            return <div>There are no albums yet</div>;

        return albums.map(album => (
            <div key={album.isrc}>
                <span>ISRC: {album.isrc}</span>
                <span className="ml-3">Title: {album.title}</span>
                <span className="ml-3">Release Year: {album.releaseYear}</span>
                <span className="ml-3">Artist: {album.artist.firstname} {album.artist.lastname}</span>
                <span className="ml-3">Content Description: {album.contentDesc}</span>
            </div>
        ));
    }

    render() {
        return (
            <div>
                <Navbar />

                <div className="mb-2">
                    Example
                </div>

                {this.renderAlbums()}
            </div>
        );
    }
}

export default Example;
