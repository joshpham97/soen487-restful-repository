package repository.core.interfaces;

import repository.core.exception.RepException;
import repository.core.pojo.Log;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ILogManager extends IManager{
    ArrayList<Log> listLog() throws SQLException;
    ArrayList<Log> listLog(LocalDateTime from, LocalDateTime to) throws SQLException;
    ArrayList<Log> listLog(LocalDateTime from, LocalDateTime to, Log.ChangeType typeOfChange) throws SQLException;
    ArrayList<Log> listLog(Log.ChangeType typeOfChange) throws SQLException;
    boolean addLog(Log log) throws RepException, SQLException;
    boolean clearLog() throws RepException;
}
