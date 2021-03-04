package repository.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDateTime;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Log implements Serializable {
    public enum ChangeType
    {
        ADD, UPDATE, DELETE;
    }

    @XmlJavaTypeAdapter(LocalDateTimeXmlAdapter.class)
    private LocalDateTime date;
    @XmlElement
    private ChangeType change;
    @XmlElement
    private String recordKey;

    public Log(LocalDateTime date, ChangeType change, String recordKey) {
        this.date = date;
        this.change = change;
        this.recordKey = recordKey;
    }
    public Log() { }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public ChangeType getChange() { return change; }
    public void setChange(ChangeType change) { this.change = change; }
    public String getRecordKey() { return recordKey; }
    public void setRecordKey(String recordKey) { this.recordKey = recordKey; }
    public String toString(){
        String str;
        str = "Date: " + date + " , change: " + change + " , Record Key: " + recordKey + "\n";
        return str;
    }
}
