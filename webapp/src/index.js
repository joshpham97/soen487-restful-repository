import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Switch, Route } from 'react-router';
import { createBrowserHistory } from 'history';
import 'bootstrap/dist/css/bootstrap.min.css';

import './styles/index.css';
import Home from './components/Home';
import Example from './components/Example';
import Example2 from './components/Example2';


const history = createBrowserHistory();

const routing = (
    <React.StrictMode>
    <Router history={history}>
        <Switch>
            <Route path="/example2" component={Example2} />
            <Route path="/example" component={Example} />
            <Route path="/" component={Home} />
        </Switch>
    </Router>
    </React.StrictMode>
);

ReactDOM.render(routing, document.getElementById('root'));
