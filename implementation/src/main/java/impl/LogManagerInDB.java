package impl;

import database.dao.LogDAO;
import repository.core.interfaces.ILogManager;
import repository.core.pojo.Log;
import repository.core.exception.RepException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class LogManagerInDB implements ILogManager {

    public ArrayList<Log> listLog() throws SQLException {
        ArrayList<Log> logs = new ArrayList<>();
        logs = LogDAO.getLog();

        return logs;
    }

    public ArrayList<Log> listLog(Log.ChangeType typeOfChange) throws SQLException {
        ArrayList<Log> logs = new ArrayList<>();
        logs = LogDAO.getLog(typeOfChange.toString());

        return logs;
    }

    public ArrayList<Log> listLog(LocalDateTime from, LocalDateTime to, Log.ChangeType typeOfChange) throws SQLException{
        ArrayList<Log> logs = new ArrayList<>();
        logs = LogDAO.getLog(from, to, typeOfChange.toString());

        return logs;
    }

    public ArrayList<Log> listLog(LocalDateTime from, LocalDateTime to) throws SQLException {
        ArrayList<Log> logs = new ArrayList<>();
        logs = LogDAO.getLog(from, to);

        return logs;
    }

    public synchronized boolean addLog(Log log) throws RepException, SQLException {
        LocalDateTime date = log.getDate();
        String typeOfChange = log.getChange().toString();
        String recordKey = log.getRecordKey();

        if(recordKey.equals("") || typeOfChange.equals(""))
        {
            throw new RepException("ERROR: MISSING VALUE");
        }

        boolean added = LogDAO.addLog(date, typeOfChange, recordKey);
        return added;
    }
    public boolean clearLog() throws RepException {
        throw new RepException("ERROR: CLEAR LOG is not yet supported.");
    }
}
