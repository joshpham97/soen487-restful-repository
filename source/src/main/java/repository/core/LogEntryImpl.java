package repository.core;

import javax.jws.WebService;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

@WebService(endpointInterface = "repository.core.LogEntry")
public class LogEntryImpl implements LogEntry {
    private CopyOnWriteArrayList<Log> logList = new CopyOnWriteArrayList<>();

    @Override
    public void addLog(String change, String recordKey) throws logFault {
        LocalDateTime date = LocalDateTime.now();
        if(change.equals("") || recordKey.equals(""))
        {
            throw new logFault("ERROR: change type or recordKey CANNOT be null!!");
        }
        else {
            logList.add(new Log(date, change, recordKey));
        }
    }
}
