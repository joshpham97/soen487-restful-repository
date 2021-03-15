import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Switch } from 'react-router';
import { Route } from 'react-router-dom';
import { createBrowserHistory } from 'history';
import 'bootstrap/dist/css/bootstrap.min.css';

import './styles/index.css';
import AlbumList from './components/AlbumList';
import AlbumFilter from './components/AlbumFilter';
import AlbumDetails from './components/AlbumDetails';
import AlbumForm from './components/AlbumForm';
import LogFilter from './components/LogFilter';
import Logs from './components/Logs';

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
            <Route path="/logs/filter" component={LogFilter} />
            <Route path="/logs" component={Logs} />
            <Route path="/" component={AlbumList} />
        </Switch>
    </Router>
    </React.StrictMode>
);


ReactDOM.render(routing, document.getElementById('root'));
