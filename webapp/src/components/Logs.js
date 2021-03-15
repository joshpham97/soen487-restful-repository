import '../styles/logs.css';

import Navbar from "./subcomponents/Navbar";
import React, { useEffect, useRef, useState} from "react";
import { withStyles } from '@material-ui/core/styles';
import { logApi, logServer} from '../endpoints/logServer';
import { soapOperations, envelopeBuilder, messageParser } from '../utilities/soapUtils';
import MuiTableContainer from '@material-ui/core/TableContainer';
import { CircularProgress, Fab, Table, TableHead, TableBody, TableRow, TableCell } from "@material-ui/core";
import FilterListIcon from "@material-ui/icons/FilterList";
import {useHistory, useLocation} from "react-router";
import DeleteIcon from "@material-ui/icons/Delete";

const windowHeight = window.innerHeight;
const tableHeight = windowHeight/1.7;

const TableContainer = withStyles({
    root: {
        height: tableHeight + 'px'
    }
})(MuiTableContainer);

const BodyTableRow = withStyles((theme) => ({
    root: {
        '&:nth-of-type(odd)': {
            backgroundColor: theme.palette.action.hover
        }
    }
}))(TableRow);

function Logs() {
    const history = useHistory();
    const location = useLocation();

    const [loaded, setLoaded] = useState(false);
    const changeTypeFilterRef = useRef('');
    const fromFilterRef = useRef('');
    const toFilterRef = useRef('');

    const [logs, setLogs] = useState([]);

    useEffect( () => {
        // Mount
        const params = location.state;
        if(params) {
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
        const soapEnvelope = envelopeBuilder(soapOperations.list, {
            from: fromFilterRef.current,
            to: toFilterRef.current,
            changeType: changeTypeFilterRef.current,
        });

        logServer.post(logApi.soapOperation, soapEnvelope, {
            headers: {
                'Content-Type': 'text/xml'
            }
        })
            .then(res => setLogs(messageParser(res.data)))
            .catch(err => {
                if(err.response)
                    alert(err.response.data);

                setLogs(null);
            })
            .finally(() => setLoaded(true));
    };

    const filterRedirect = () => {
        history.push({
            pathname: '/logs/filter',
            state: location.state
        });
    };

    const renderLogList = () => {
        if(!loaded)
            return <CircularProgress color="inherit" />
        else if(!logs)
            return <div>An error occurred while getting the logs.</div>;
        else if(logs.length === 0) {
            return (
                <React.Fragment>
                    <div>There are no matches</div>
                    {addFilter()}
                </React.Fragment>
            );
        }

        return (
            <React.Fragment>
                <TableContainer>
                    <Table size="small" stickyHeader>
                        <TableHead>
                            <TableRow>
                                <TableCell align="center"><b>ISRC</b></TableCell>
                                <TableCell align="center"><b>Change Type</b></TableCell>
                                <TableCell align="center"><b>Date/Time</b></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {logs.map((log, n) => (
                                <BodyTableRow key={n}>
                                    <TableCell align="center">{log.recordKey}</TableCell>
                                    <TableCell align="center">{log.change}</TableCell>
                                    <TableCell align="center">{log.date}</TableCell>
                                </BodyTableRow>

                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>

                {addFilter()}
            </React.Fragment>
        );
    };

    const addFilter = () => {
        return (
            <div className="mt-3 mb-3">
                <Fab size="medium" onClick={filterRedirect} >
                    <FilterListIcon />
                </Fab>
                &nbsp;&nbsp;&nbsp;
                <Fab size="medium" onClick={clearLog} >
                    <DeleteIcon />
                </Fab>
            </div>
        );
    };

    const clearLog = () => {
        const soapEnvelope = envelopeBuilder(soapOperations.clear);

        logServer.post(logApi.soapOperation, soapEnvelope, {
            headers: {
                'Content-Type': 'text/xml'
            }
        })
            .then(res => messageParser(res.data))
            .catch(err => alert(err));
    };

    return (
        <React.Fragment>
            <Navbar />

            <div id="logList">
                <h3>Logs</h3>
                {renderLogList()}
            </div>
        </React.Fragment>
    );
}
export default Logs;