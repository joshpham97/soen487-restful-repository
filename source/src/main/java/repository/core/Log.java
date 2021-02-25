package repository.core;

import java.time.LocalDateTime;

public class Log {
    private LocalDateTime date;
    private String change;
    private String recordKey;

    public Log(LocalDateTime date, String change, String recordKey) {
        this.date = date;
        this.change = change;
        this.recordKey = recordKey;
    }
    public Log() { }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public String getChange() { return change; }
    public void setChange(String change) { this.change = change; }
    public String getRecordKey() { return recordKey; }
    public void setRecordKey(String recordKey) { this.recordKey = recordKey; }
}
