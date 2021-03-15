package repository.core.interfaces;

import repository.core.exception.RepException;
import repository.core.pojo.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ILogManager extends IManager{
    ArrayList<Log> listLog();
    ArrayList<Log> listLog(LocalDateTime from, LocalDateTime to);
    ArrayList<Log> listLog(LocalDateTime from, LocalDateTime to, Log.ChangeType typeOfChange);
    ArrayList<Log> listLog(Log.ChangeType typeOfChange);
    boolean addLog(Log log) throws RepException;
    boolean clearLog() throws RepException;
}
