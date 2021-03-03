package com.example.soap.service;

import factories.LogManagerFactory;
import repository.core.ILogManager;
import repository.core.Log;
import repository.core.LogFault;

import javax.jws.WebService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

@WebService(endpointInterface = "com.example.soap.service.LogEntry")
public class LogEntryImpl implements LogEntry {
    private CopyOnWriteArrayList<Log> logList = new CopyOnWriteArrayList<>();
    private ILogManager logManager = LogManagerFactory.loadManager();

    @Override
    public ArrayList<Log> listLog(String from, String to, String changeType) throws LogFault {
        ArrayList<Log> logs = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fromDateTime = null;
        LocalDateTime toDateTime = null;

        //Format date if not null
        if(!from.equals(""))
        {
            fromDateTime = LocalDateTime.parse(from, formatter);
        }
        if(!to.equals("")){
            toDateTime = LocalDateTime.parse(to, formatter);
        }

        //FILTERING
        if((fromDateTime != null && toDateTime != null && !changeType.equals("")) || (fromDateTime == null && toDateTime != null && !changeType.equals("")) || (fromDateTime != null && toDateTime == null && !changeType.equals("")))
        {
            logs = logManager.listLog(fromDateTime, toDateTime, changeType);
        }
        else if((fromDateTime != null && toDateTime == null && changeType.equals("")) || (fromDateTime == null && toDateTime != null && changeType.equals("")) || (fromDateTime != null && toDateTime != null && changeType.equals("")))
        {
            logs = logManager.listLog(fromDateTime, toDateTime);
        }
        else if(fromDateTime == null && toDateTime == null && !changeType.equals(""))
        {
            logs = logManager.listLog(changeType);
        }
        else
        {
            logs = logManager.listLog();
        }
        return logs;
    }
}
