import '../styles/home.css';

import { Component } from 'react';
import Navbar from './Navbar';

class Home extends Component {
    render() {
        return (
            <div>
                <Navbar />

                <div>
                    Home
                </div>
            </div>
        );
    }
}

export default Home;