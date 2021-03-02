package com.example.soap.service;

import database.dao.LogDAO;
import factories.LogManagerFactory;
import org.glassfish.grizzly.http.util.TimeStamp;
import repository.core.ILogManager;
import repository.core.Log;
import repository.core.LogFault;

import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

@WebService(endpointInterface = "com.example.soap.service.LogEntry")
public class LogEntryImpl implements LogEntry {
    private CopyOnWriteArrayList<Log> logList = new CopyOnWriteArrayList<>();
    private ILogManager logManager = LogManagerFactory.loadManager();

    /**
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

    @Override
    public String listLogs() throws LogFault {

        ArrayList<Log> logs = new ArrayList<>();
        logs = logManager.listLog();

        return logs.toString();
    }
     */

    @Override
    public String listLogFilter(String from, String to, String changeType) throws LogFault {
        ArrayList<Log> logs = new ArrayList<>();
        if((!from.equals("") && to.equals("")) || (from.equals("") && !to.equals("")))
        {
            throw new LogFault("ERROR: Missing data!!");
        }
        else if(!from.equals("") && !to.equals("")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime fromDateTime = LocalDateTime.parse(from, formatter);
            LocalDateTime toDateTime = LocalDateTime.parse(to, formatter);
            logs = logManager.listLog(fromDateTime, toDateTime, changeType);
        }
        else if(from.equals("") && !changeType.equals(""))
        {
            logs = logManager.listLog(changeType);
        }
        else {
            logs = logManager.listLog();
        }
        return logs.toString();
    }

    @Override
    public ArrayList<Log> listLog(String from, String to, String changeType) throws LogFault {
        ArrayList<Log> logs = new ArrayList<>();
        if((!from.equals("") && to.equals("")) || (from.equals("") && !to.equals("")))
        {
            throw new LogFault("ERROR: Missing data!!");
        }
        else if(!from.equals("") && !to.equals("")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime fromDateTime = LocalDateTime.parse(from, formatter);
            LocalDateTime toDateTime = LocalDateTime.parse(to, formatter);
            logs = logManager.listLog(fromDateTime, toDateTime, changeType);
        }
        else if(from.equals("") && !changeType.equals(""))
        {
            logs = logManager.listLog(changeType);
        }
        else {
            logs = logManager.listLog();
        }
        return logs;
    }
}
