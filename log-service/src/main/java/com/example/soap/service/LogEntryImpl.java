package com.example.soap.service;

import repository.core.Log;
import repository.core.LogFault;

import javax.jws.WebService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

@WebService(endpointInterface = "com.example.soap.service.LogEntry")
public class LogEntryImpl implements LogEntry {
    private CopyOnWriteArrayList<Log> logList = new CopyOnWriteArrayList<>();

    @Override
    public void addLog(String change, String recordKey) throws LogFault {
        LocalDateTime date = LocalDateTime.now();
        if(change.equals("") || recordKey.equals(""))
        {
            throw new LogFault("ERROR: change type or recordKey CANNOT be null!!");
        }
        else {
            logList.add(new Log(date, change, recordKey));
        }
    }

    @Override
    public String listLog() throws LogFault {

        StringBuilder str = new StringBuilder("");
        for(Log log: logList){
             str.append(log.toString() + "\n");
        }
        return str.toString();
    }
}
