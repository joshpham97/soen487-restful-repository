import {withStyles} from "@material-ui/core/styles";
import MuiAddIcon from "@material-ui/icons/Add";
import AlbumList from "./AlbumList";
import {useHistory, useLocation} from "react-router";
import React from "react";
import Navbar from "./Navbar";
import ArrowBackIosRoundedIcon from "@material-ui/icons/ArrowBackIosRounded";
import {Button, FormControl, TextField} from "@material-ui/core";
import { useState, useEffect, useRef } from 'react';
import FilterListIcon from "@material-ui/icons/FilterList";
import {albumApi, albumServer} from "../endpoints/albumServer";
import {logServer, logApi} from "../endpoints/logServer";
import axios from "axios";


function LogList() {
    const history = useHistory();
    const location = useLocation();

    const [recordKey, setRecordKey] = useState('');
    const [changeType, setChangeType] = useState('');
    const [fromDateTime, setFromDateTime] = useState('');
    const [toDateTime, setToDateTime] = useState('');

    const [recordKeyError, setRecordKeyError] = useState('');
    const [changeTypeError, setChangeTypeError] = useState('');
    const [fromDateTimeError, setFromDateTimeError] = useState('');
    const [toDateTimeError, setToDateTimeError] = useState('');
/**
    useEffect( () => {
        // Mount
        const params = location.state;
        if(params && params.changeType) {
            if(params.filter.changeType)
                setChangeType(params.filter.changeType);
            if(params.filter.from)
                setFromDateTime(params.filter.from);
            if(params.filter.to)
                setToDateTime(params.filter.to);
        }
    }, [location.state]);
*/
    const getList = () => {
        /**
        let str ="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.soap.example.com/\">"+
            "<soapenv:Header/>" +
            "<soapenv:Body> <ser:listLog>" +
            "<from>2021-03-04 12:02:50</from>"+
            "<to>2021-03-04 12:02:50</to>" +
            "<changeType>add</changeType>" +
            "</ser:listLog> </soapenv:Body> </soapenv:Envelope>";
         */
        let xmls='<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.soap.example.com/">\
               <soapenv:Header/>\
               <soapenv:Body>\
                  <ser:listLog>\
                     <!--Optional:-->\
                     <from>2021-03-04 12:02:50</from>\
                     <!--Optional:-->\
                     <to>2021-03-04 12:02:50</to>\
                     <!--Optional:-->\
                     <changeType>add</changeType>\
                  </ser:listLog>\
               </soapenv:Body>\
            </soapenv:Envelope>';
        axios.post('http://localhost:8090/log', xmls,
            {headers:
                    {'Content-Type': 'text/xml', 'Access-Control-Allow-Origin': '*'}
            }).then(res=>{
            console.log(res);
        }).catch(err=>{console.log(err)});
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
                        <TextField label="changeType" value={changeType} error={changeTypeError !== ''} helperText={changeTypeError}
                                   onChange={(e) => setChangeType(e.currentTarget.value)} />
                    </FormControl>

                    <FormControl className="formColumn">
                        <TextField label="fromDateTime" value={fromDateTime} error={fromDateTimeError !== ''} helperText={fromDateTimeError}
                                   onChange={(e) => setFromDateTime(e.currentTarget.value)} />
                    </FormControl>

                    <FormControl className="formColumn">
                        <TextField label="toDateTime" value={toDateTime} error={toDateTimeError !== ''} helperText={toDateTimeError}
                                   onChange={(e) => setToDateTime(e.currentTarget.value)} />
                    </FormControl>
                </div>
                <Button className="mr-3" variant="contained" color="primary" startIcon={<FilterListIcon />} onClick={getList}>Filter</Button>
            </div>
        </React.Fragment>
    );

}
export default LogList;