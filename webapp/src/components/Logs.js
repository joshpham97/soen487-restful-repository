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

    const [currentAlbum, setCurrentLog] = useState(null);
    const recordKeyFilterRef = useRef('');
    const [logs, setLogs] = useState([]);
    const [loaded, setLoaded] = useState(false);

    useEffect( () => {
        // Mount
        const params = location.state;
        if(params) {
            if (params.album)
                setCurrentLog(params.log);

            if(params.filter) {
                if (params.filter.recordKey)
                    recordKeyFilterRef.current = params.filter.recordKey;
            }
        }

        getLogList();
        const interval = setInterval(getLogList, 1000);

        // Unmount
        return () => clearInterval(interval);
    }, [location.state]);

    const getLogList = () => {
        logServer.get(logApi.logs, {
            params: {
                recordKey: recordKeyFilterRef.current,
            }
        })
            .then(res => {
                setLogs(res.data.filter((log) => {
                    return log.recordKey.match(new RegExp(recordKeyFilterRef.current, 'i'));
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
            </div>
        </React.Fragment>
    );

}
export default Logs;