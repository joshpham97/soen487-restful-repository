package com.example.soap.service;

import factories.ManagerFactory;
import repository.core.interfaces.ILogManager;
import repository.core.pojo.Log;
import repository.core.exception.LogException;
import repository.core.exception.RepException;

import javax.jws.WebService;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

@WebService(endpointInterface = "com.example.soap.service.LogEntry")
public class LogEntryImpl implements LogEntry {
    private ILogManager logManager = (ILogManager) ManagerFactory.LOG.getManager();

    @Override
    public ArrayList<Log> listLog(String from, String to, String changeType) throws LogException{
        try {
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
                    throw new LogException("Enter a valid ChangeType (CASE SENSITIVE): ADD , UPDATE, DELETE");
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
                    throw new LogException("ERROR: Date format should be yyyy-MM-dd HH:mm:ss");
                }
            }
            if(to != null && !to.equals("")){
                try {
                    toDateTime = LocalDateTime.parse(to, formatter);
                }
                catch (DateTimeParseException pe)
                {
                    throw new LogException("ERROR: Date format should be yyyy-MM-dd HH:mm:ss");
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
        } catch(LogException | SQLException ex){
            throw new LogException("Error getting the list of logs!");
        }
    }

    @Override
    public String clearLog() throws LogException {
        try {
            logManager.clearLog();

            return "Log cleared";
        } catch(RepException re) {
            throw new LogException(re.getMessage());
        }
    }
}
