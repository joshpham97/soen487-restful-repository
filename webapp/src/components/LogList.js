import {useHistory, useLocation} from "react-router";
import React from "react";
import Navbar from "./subcomponents/Navbar";
import ArrowBackIosRoundedIcon from "@material-ui/icons/ArrowBackIosRounded";
import {Button, FormControl, Input, InputLabel, TextField} from "@material-ui/core";
import { useState, useEffect, useRef } from 'react';
import FilterListIcon from "@material-ui/icons/FilterList";

function LogList() {
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
        let valid = true;

        if(fromDateTime.length !== 0)
        {
            if(fromDateTime.length === 19)
            {
                if(isNaN(Date.parse(fromDateTime))){
                    alert("Date format should be: yyyy-MM-dd HH:mm:ss")
                    valid = false;
                }
            }
            else{
                alert("Date format should be: yyyy-MM-dd HH:mm:ss")
                valid = false;
            }
        }
        if(toDateTime.length !== 0)
        {
            if(toDateTime.length === 19)
            {
                if(isNaN(Date.parse(toDateTime))){
                    alert("Date format should be: yyyy-MM-dd HH:mm:ss")
                    valid = false;
                }
            }
            else{
                alert("Date format should be: yyyy-MM-dd HH:mm:ss")
                valid = false;
            }
        }

        if(valid)
        {
            history.push({
                pathname: '/logs',
                state: {
                    filter: {
                        from: fromDateTime,
                        to: toDateTime,
                        changeType: changeType.toUpperCase()
                    }
                }
            });
        }
    };

    const backRedirect = () => {
        let state = location.state ? location.state : {};
        state.log = location.state && location.state.log ? location.state.log : null;

        history.push({
            pathname: '/logs',
            state: state
        });
    };

    return (
        <React.Fragment>
            <Navbar />

            <div id="logForm">
                <ArrowBackIosRoundedIcon className="back" fontSize="large" onClick={backRedirect} />

                <div className="formRow">
                    <FormControl className="formColumn">
                        <InputLabel htmlFor="logChangeType">ChangeType</InputLabel>
                        <Input id="logChangeType" value={changeType}
                               onChange={(e) => setChangeType(e.currentTarget.value)}/>
                    </FormControl>

                    <FormControl className="formColumn">
                        <InputLabel htmlFor="fromDateTime">From</InputLabel>
                        <Input id="fromDateTime" value={fromDateTime}
                               onChange={(e) => setFromDateTime(e.currentTarget.value)}/>
                    </FormControl>

                    <FormControl className="formColumn">
                        <InputLabel htmlFor="toDateTime">To</InputLabel>
                        <Input id="toDateTime" value={toDateTime}
                               onChange={(e) => setToDateTime(e.currentTarget.value)}/>
                    </FormControl>
                </div>
                <Button className="mr-3" variant="contained" color="primary" startIcon={<FilterListIcon />} onClick={getList}>Filter</Button>
            </div>
        </React.Fragment>
    );
}
export default LogList;