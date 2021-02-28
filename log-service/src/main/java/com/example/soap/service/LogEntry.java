package com.example.soap.service;

import org.glassfish.grizzly.http.util.TimeStamp;
import repository.core.Log;
import repository.core.LogFault;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.time.LocalDateTime;
import java.util.ArrayList;

@WebService
@SOAPBinding
public interface LogEntry {
    /**
    @WebMethod(operationName = "addLog")
    public void addLog(@WebParam(name="change") String change, @WebParam(name="recordKey") String recordKey) throws LogFault;

    @WebMethod(operationName = "listLog")
    public String listLog() throws LogFault;
    */
    @WebMethod(operationName = "listLogs")
    public String listLogs() throws LogFault;
    @WebMethod(operationName = "listLogFilter")
    public String listLogFilter(@WebParam(name="from") String from, @WebParam(name="to") String to, @WebParam(name="changeType") String changeType) throws LogFault;


}
