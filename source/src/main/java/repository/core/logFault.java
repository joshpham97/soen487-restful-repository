package repository.core;

import javax.xml.ws.WebFault;

@WebFault(name = "logFault")
public class logFault extends Exception {
    public logFault(String message) {
        super(message);
    }
}
