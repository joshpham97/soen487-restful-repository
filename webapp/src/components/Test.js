import '../styles/home.css';

import { Component } from 'react';
import { albumServer, albumApi } from '../endpoints/albumServer';
import Navbar from './Navbar';

class Test extends Component {
    constructor(props) {
        super(props);

        this.state = {
            albums: null,
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

    getAlbums = async () => {
        try {
            const res = await albumServer.get(albumApi.albums);

            this.setState({
                albums: res.data,
                errorMessage: null
            });
        } catch {
            this.setState({
                albums: null,
                errorMessage: "An error occurred while getting the albums"
            });
        }
    }

    renderAlbums = () => {
        if(this.state.albums) {
            return (
                <div>
                    {this.state.albums}
                </div>
            );
        }

        return (
            <div>
                {this.state.errorMessage}
            </div>
        );
    }

    render() {
        return (
            <div>
                <Navbar />

                <div className="mb-2">
                    Test
                </div>

                {this.renderAlbums()}
            </div>
        );
    }
}

export default Test;
