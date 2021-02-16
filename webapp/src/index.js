import React from 'react';
import ReactDOM from 'react-dom';
import { Router, Switch, Route } from 'react-router';
import { createBrowserHistory } from 'history';
import 'bootstrap/dist/css/bootstrap.min.css';

import './styles/index.css';
import Home from './components/Home';
import Test from './components/Test';
// import reportWebVitals from './reportWebVitals';

const history = createBrowserHistory();

const routing = (
    <React.StrictMode>
    <Router history={history}>
        <Switch>
            <Route path="/t" component={Test} />
            <Route path="/" component={Home} />
        </Switch>
    </Router>
    </React.StrictMode>
);

ReactDOM.render(routing, document.getElementById('root'));

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals();
