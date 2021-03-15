package com.example.soap.service;

import repository.core.pojo.Log;
import repository.core.exception.LogException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;

@WebService
@SOAPBinding
public interface LogEntry {
    @WebMethod(operationName = "listLog")
    public ArrayList<Log> listLog(@WebParam(name="from") String from, @WebParam(name="to") String to, @WebParam(name="changeType") String changeType) throws LogException;
    @WebMethod(operationName = "clearLog")
    public String clearLog() throws LogException;
}
