import Navbar from "./subcomponents/Navbar";
import React, {Component, useEffect, useRef, useState} from "react";
import { logApi, logServer} from '../endpoints/logServer';
import {Button, Fab, Slide} from "@material-ui/core";
import FilterListIcon from "@material-ui/icons/FilterList";
import {useHistory, useLocation} from "react-router";
import DeleteIcon from "@material-ui/icons/Delete";

function Logs() {
    const history = useHistory();
    const location = useLocation();

    const [loaded, setLoaded] = useState(false);
    const changeTypeFilterRef = useRef('');
    const fromFilterRef = useRef('');
    const toFilterRef = useRef('');

    const [logs, setLogs] = useState([]);
    const [currentLog, setCurrentLog] = useState(null);

    useEffect( () => {
        // Mount
        const params = location.state;
        if(params) {
            if (params.log)
                setCurrentLog(params.log);

            if(params.filter) {
                if (params.filter.changeType)
                    changeTypeFilterRef.current = params.filter.changeType;
                if (params.filter.from)
                    fromFilterRef.current = params.filter.from;
                if (params.filter.to)
                    toFilterRef.current = params.filter.to;
            }
        }

        getLogList();
        const interval = setInterval(getLogList, 1000);

        // Unmount
        return () => clearInterval(interval);
    }, [location.state]);

    const getLogList = () => {
    logServer.get(logApi.get, {
        params: {
            changeType: changeTypeFilterRef.current,
            from: fromFilterRef.current,
            to: toFilterRef.current
        }
    })
        .then(res => {
            setLogs(res.data.filter((log) => {
                return log.change.match(new RegExp(changeTypeFilterRef.current, 'i'));
            }));
        })
        .catch(err => {
            console.log(err);
            setLogs(null);
        })
        .finally(() => setLoaded(true));
    };

    const filterRedirect = () => {
        history.push({
            pathname: '/logList',
            state: location.state
        });
    };

    const renderLogList = () => {

        if(!logs)
            return <div>An error occurred while getting the logs. Date format should be: yyyy-MM-dd HH:mm:ss</div>;
        else if(logs.length === 0)
            return <div>There are no matches</div>;


        return logs.map(log => (
            <div key={log.date}>
                <span>changeType: {log.change}</span>
                <span className="ml-3">Date: {log.date}</span>
                <span className="ml-3">ISRC: {log.recordKey}</span>
            </div>

        ));

    }

    const addFilter = () => {
        return (
            <React.Fragment>
                <div className="mt-3 mb-3">
                    <Fab size="medium" onClick={filterRedirect} >
                        <FilterListIcon />
                    </Fab>
                    &nbsp;&nbsp;&nbsp;
                    <Fab size="medium" onClick={clearLog} >
                        <DeleteIcon />
                    </Fab>
                </div>
            </React.Fragment>
        );
    }

    const clearLog = () => {
        logServer.delete(logApi.delete)
            .then(res => {
                history.push({
                    pathname: '/logs',
                    state: location.state
                });
            })
            .catch(err => alert("ERROR: CLEAR LOG is not yet supported."));
    };

    return (
        <React.Fragment>
            <Navbar />

            <div id="albumList">
                <h3>Logs</h3>
                {renderLogList()}
                {addFilter()}
            </div>
        </React.Fragment>
    );
}
export default Logs;