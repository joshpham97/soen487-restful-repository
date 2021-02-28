package repository.core;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ILogManager {
    ArrayList<Log> listLog();
    ArrayList<Log> listLog(LocalDateTime from, LocalDateTime to);
   // ArrayList<Log> getLog(LocalDateTime from, LocalDateTime to);
    //ArrayList<Log> getLog(String typeOfChange)
    boolean addLog(Log log);
}
