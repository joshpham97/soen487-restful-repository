import '../styles/logForm.css';

import {useHistory, useLocation} from "react-router";
import React from "react";
import Navbar from "./subcomponents/Navbar";
import ArrowBackIosRoundedIcon from "@material-ui/icons/ArrowBackIosRounded";
import {Button, FormControl, Input, InputLabel } from "@material-ui/core";
import { useState, useEffect } from 'react';
import FilterListIcon from "@material-ui/icons/FilterList";
import RotateLeftIcon from "@material-ui/icons/RotateLeft";

function LogFilter() {
    const history = useHistory();
    const location = useLocation();

    const [changeType, setChangeType] = useState('');
    const [fromDateTime, setFromDateTime] = useState('');
    const [toDateTime, setToDateTime] = useState('');

    useEffect( () => {
        // Mount
        const params = location.state;
        if(params && params.filter) {
            if(params.filter.changeType)
                setChangeType(params.filter.changeType);
            if(params.filter.from)
                setFromDateTime(params.filter.from);
            if(params.filter.to)
                setToDateTime(params.filter.to);
        }
    }, [location.state]);

    const getList = () => {

        let modifiedFromDate, modifiedToDate;
        if(fromDateTime.length !== 0)
        {
            modifiedFromDate = fromDateTime.replace("T", " ") + ":00";
        }
        else{
            modifiedFromDate = "";
        }

        if(toDateTime.length !== 0)
        {
            modifiedToDate = toDateTime.replace("T", " ") + ":00";
        }
        else{
            modifiedToDate = "";
        }

        history.push({
            pathname: '/logs',
            state: {
                filter: {
                    from: modifiedFromDate,
                    to: modifiedToDate,
                    changeType: changeType.toUpperCase()
                }
            }
        });
    };

    const backRedirect = () => {
        let state = location.state ? location.state : {};
        state.log = location.state && location.state.log ? location.state.log : null;

        history.push({
            pathname: '/logs',
            state: state
        });
    };

    const logsRedirect = () => {
        history.push('/logs');
    };

    return (
        <React.Fragment>
            <Navbar />

            <div id="logForm">
                <ArrowBackIosRoundedIcon className="back" fontSize="large" onClick={backRedirect} />

                <h3>
                    Filter Logs
                </h3>

                <div className="formRow">
                    <FormControl className="formColumn">
                        <label htmlFor="logChangeType">ChangeType:</label>
                        <select id="logChangeType" onChange={(e) => setChangeType(e.currentTarget.value)}>
                            <option value="">ChangeType</option>
                            <option value="ADD">ADD</option>
                            <option value="UPDATE">UPDATE</option>
                            <option value="DELETE">DELETE</option>
                        </select>
                    </FormControl>
                </div>

                <div className="formRow">
                    <FormControl className="formColumn">
                        <label htmlFor="fromDateTime">From:</label>
                        <input type="datetime-local" id="fromDateTime"
                               min="2021-03-01T00:00" max="2021-03-31T00:00" pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}" onChange={(e) => setFromDateTime(e.currentTarget.value)}/>
                    </FormControl>

                    <FormControl className="formColumn">
                        <label htmlFor="toDateTime">To:</label>
                        <input type="datetime-local" id="toDateTime"
                               min="2021-03-01T00:00" max="2021-03-31T00:00" pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}" onChange={(e) => setToDateTime(e.currentTarget.value)}/>
                    </FormControl>
                </div>

                <Button className="mr-3" variant="contained" color="primary" startIcon={<FilterListIcon />} onClick={getList}>Filter</Button>
                <Button variant="contained" color="secondary" startIcon={<RotateLeftIcon />} onClick={logsRedirect}>Reset</Button>
            </div>
        </React.Fragment>
    );
}
export default LogFilter;