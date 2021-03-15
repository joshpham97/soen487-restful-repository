package com.example.soap.service;

import factories.ManagerFactory;
import repository.core.ILogManager;
import repository.core.Log;
import repository.core.LogFault;
import repository.core.RepException;

import javax.jws.WebService;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.IllegalFormatException;

@WebService(endpointInterface = "com.example.soap.service.LogEntry")
public class LogEntryImpl implements LogEntry {
    private ILogManager logManager = (ILogManager) ManagerFactory.LOG.getManager();

    @Override
    public ArrayList<Log> listLog(String from, String to, String changeType) throws LogFault {
        ArrayList<Log> logs = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fromDateTime = null;
        LocalDateTime toDateTime = null;
        Log.ChangeType type = null;

        if(!changeType.equals(""))
        {
            if(changeType.equals("ADD") || changeType.equals("UPDATE") || changeType.equals("DELETE"))
            {
                type = Log.ChangeType.valueOf(changeType);
            }
            else{
                throw new LogFault("Enter a valid ChangeType (CASE SENSITIVE): ADD , UPDATE, DELETE");
            }
        }

        //Format date if not null
        if(from != null && !from.equals(""))
        {
            try {
                fromDateTime = LocalDateTime.parse(from, formatter);
            }
            catch (DateTimeParseException pe)
            {
                throw new LogFault("ERROR: Date format should be yyyy-MM-dd HH:mm:ss");
            }
        }
        if(to != null && !to.equals("")){
            try {
                toDateTime = LocalDateTime.parse(to, formatter);
            }
            catch (DateTimeParseException pe)
            {
                throw new LogFault("ERROR: Date format should be yyyy-MM-dd HH:mm:ss");
            }
        }

        //FILTERING
        if((fromDateTime != null && toDateTime != null && !changeType.equals("")) || (fromDateTime == null && toDateTime != null && !changeType.equals("")) || (fromDateTime != null && toDateTime == null && !changeType.equals("")))
        {
            logs = logManager.listLog(fromDateTime, toDateTime, type);
        }
        else if((fromDateTime != null && toDateTime == null && changeType.equals("")) || (fromDateTime == null && toDateTime != null && changeType.equals("")) || (fromDateTime != null && toDateTime != null && changeType.equals("")))
        {
            logs = logManager.listLog(fromDateTime, toDateTime);
        }
        else if(fromDateTime == null && toDateTime == null && !changeType.equals(""))
        {
            logs = logManager.listLog(type);
        }
        else
        {
            logs = logManager.listLog();
        }
        return logs;
    }

    @Override
    public String clearLog() throws LogFault {
        try {
            logManager.clearLog();

            return "Log cleared";
        } catch(RepException re) {
            throw new LogFault(re.getMessage());
        }
    }
}
