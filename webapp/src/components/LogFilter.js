import '../styles/logForm.css';

import {useHistory, useLocation} from "react-router";
import React from "react";
import Navbar from "./subcomponents/Navbar";
import ArrowBackIosRoundedIcon from "@material-ui/icons/ArrowBackIosRounded";
import {Button, FormControl, TextField, Select, MenuItem, InputLabel} from "@material-ui/core";
import { useState, useEffect } from 'react';
import FilterListIcon from "@material-ui/icons/FilterList";
import RotateLeftIcon from "@material-ui/icons/RotateLeft";

const changeTypes = [
    'ADD',
    'UPDATE',
    'DELETE'
];

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
                setFromDateTime(formatDateTimeForInput(params.filter.from));
            if(params.filter.to)
                setToDateTime(formatDateTimeForInput(params.filter.to));
        }
    }, [location.state]);

    const formatDateTimeForInput = (dateTime) => {
        return dateTime.substring(0, dateTime.length - 3).replace(' ', 'T');
    };

    const formatDateTimeForApiCall = (dateTime) => {
        return dateTime && dateTime !== '' ? dateTime.replace("T", " ") + ':00' : '';
    };

    const getList = () => {
        history.push({
            pathname: '/logs',
            state: {
                filter: {
                    from: formatDateTimeForApiCall(fromDateTime),
                    to: formatDateTimeForApiCall(toDateTime),
                    changeType: changeType
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

    const renderChangeTypeOptions = () => {
        return changeTypes.map((c) => {
            if(c === changeType)
                return <option key={c} value={c} selected>{c}</option>;
            else
                return <option key={c} value={c}>{c}</option>;
        });
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
                        <InputLabel shrink id={"logChangeTypeLabel"} style={{width: "100px"}}>Change Type</InputLabel>
                        <Select labelId={"logChangeTypeLabel"} native id="logChangeType" value={changeType}
                                onChange={(e) => setChangeType(e.currentTarget.value)}>
                            <option value="">All</option>
                            {renderChangeTypeOptions()}
                        </Select>
                    </FormControl>
                </div>

                <div className="formRow">
                    <FormControl className="formColumn">
                        <TextField type="datetime-local" label="From" variant="outlined" value={fromDateTime} InputLabelProps={{shrink: true}}
                                   onChange={(e) => setFromDateTime(e.currentTarget.value)}/>
                    </FormControl>

                    <FormControl className="formColumn">
                        <TextField type="datetime-local" label="To" value={toDateTime} variant="outlined" InputLabelProps={{shrink: true}}
                                   onChange={(e) => setToDateTime(e.currentTarget.value)}/>
                    </FormControl>
                </div>

                <Button className="mr-3" variant="contained" color="primary" startIcon={<FilterListIcon />} onClick={getList}>Filter</Button>
                <Button variant="contained" color="secondary" startIcon={<RotateLeftIcon />} onClick={logsRedirect}>Reset</Button>
            </div>
        </React.Fragment>
    );
}
export default LogFilter;