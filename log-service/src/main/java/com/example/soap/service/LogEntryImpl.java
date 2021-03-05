package com.example.soap.service;

import factories.ManagerFactory;
import repository.core.ILogManager;
import repository.core.Log;
import repository.core.LogFault;

import javax.jws.WebService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@WebService(endpointInterface = "com.example.soap.service.LogEntry")
public class LogEntryImpl implements LogEntry {
    private ILogManager logManager = (ILogManager) ManagerFactory.LOG.getManager();

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

    @Override
    public String clearLog() throws LogFault {
        throw new LogFault("ERROR: CLEAR LOG is not yet supported.");
    }
}
