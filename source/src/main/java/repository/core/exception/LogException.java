package repository.core.exception;

import javax.xml.ws.WebFault;

@WebFault(name = "LogFault")
public class LogException extends Exception {
    public LogException(String message) {
        super(message);
    }
}
