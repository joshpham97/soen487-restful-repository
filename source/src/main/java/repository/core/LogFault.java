package repository.core;

import javax.xml.ws.WebFault;

@WebFault(name = "LogFault")
public class LogFault extends Exception {
    public LogFault(String message) {
        super(message);
    }
}
