package repository.core;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.util.Date;

public class LocalDateTimeXmlAdapter extends XmlAdapter<String, LocalDateTime> {

    @Override
    public String marshal(LocalDateTime arg0) throws Exception {
        return arg0.toString();
    }

    @Override
    public LocalDateTime unmarshal(String arg) throws Exception {
        return LocalDateTime.parse(arg);
    }

}