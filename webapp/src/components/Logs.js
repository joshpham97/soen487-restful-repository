import Navbar from "./Navbar";
import React, {Component, useEffect, useRef, useState} from "react";
import { logApi, logServer} from '../endpoints/logServer';
import {albumApi, albumServer} from "../endpoints/albumServer";
import AlbumCover from "./subcomponents/AlbumCover";
import {Fab, Slide} from "@material-ui/core";
import FilterListIcon from "@material-ui/icons/FilterList";
import MuiAddIcon from '@material-ui/icons/Add';
import {withStyles} from "@material-ui/core/styles";
import {useHistory, useLocation} from "react-router";

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
                </div>
            </React.Fragment>
        );
    }

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