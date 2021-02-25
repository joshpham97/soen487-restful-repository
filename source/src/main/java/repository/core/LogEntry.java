package repository.core;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.time.LocalDateTime;

@WebService
@SOAPBinding
public interface LogEntry {
    @WebMethod(operationName = "addLog")
    public void addLog(@WebParam(name="change") String change, @WebParam(name="recordKey") String recordKey) throws logFault;

}
