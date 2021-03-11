import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Switch } from 'react-router';
import { Route } from 'react-router-dom';
import { createBrowserHistory } from 'history';
import 'bootstrap/dist/css/bootstrap.min.css';

import './styles/index.css';
import Home from './components/Home';
import Example from './components/Example';
import Example2 from './components/Example2';
import AlbumList from './components/AlbumList';
import AlbumFilter from './components/AlbumFilter';
import AlbumDetails from './components/AlbumDetails';
import AlbumForm from './components/AlbumForm';
import Logs from './components/Logs';
import LogList from './components/LogList';

const history = createBrowserHistory();

const routing = (
    <React.StrictMode>
    <Router history={history}>
        <Switch>
            <Route path="/albums/edit" component={AlbumForm} />
            <Route path="/albums/add" component={AlbumForm} />
            <Route path="/albums/get/:isrc" component={AlbumDetails} />
            <Route path="/albums/get" component={AlbumDetails} />
            <Route path="/albums/filter" component={AlbumFilter} />
            <Route path="/albums" component={AlbumList} />
            <Route path="/example2" component={Example2} />
            <Route path="/example" component={Example} />
            <Route path="/logs" component={Logs} />
            <Route path="/logList" component={LogList} />
            <Route path="/" component={Home} />
        </Switch>
    </Router>
    </React.StrictMode>
);

ReactDOM.render(routing, document.getElementById('root'));
