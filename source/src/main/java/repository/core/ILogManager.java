package repository.core;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ILogManager extends IManager{
    ArrayList<Log> listLog();
    ArrayList<Log> listLog(LocalDateTime from, LocalDateTime to);
    ArrayList<Log> listLog(LocalDateTime from, LocalDateTime to, String typeOfChange);
    ArrayList<Log> listLog(String typeOfChange);
    boolean addLog(Log log);
    boolean clearLog() throws RepException;
}
