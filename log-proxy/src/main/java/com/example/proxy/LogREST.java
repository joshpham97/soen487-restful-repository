package com.example.proxy;

import com.example.soap.service.Log;
import com.example.soap.service.LogEntry;
import com.example.soap.service.LogEntryImplService;


import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("log")
public class LogREST {

    private LogEntryImplService service = new LogEntryImplService();
    LogEntry port = service.getLogEntryImplPort();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listLog(@QueryParam("from") String from, @QueryParam("to") String to, @QueryParam("changeType") String changeType) {
        try {
            if(from == null)
                from="";
            if(to == null)
                to="";
            if(changeType == null)
                changeType="";

            List<Log> logs = port.listLog(from, to, changeType);
            GenericEntity<List<Log>> entity = new GenericEntity<List<Log>>(logs) {};

            return Response.status(Response.Status.OK)
                    .entity(entity)
                    .build();
        } catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while trying to get the list of logs. Date Format should be: 'yyyy-MM-dd HH:mm:ss' ")
                    .build();
        }
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLogs() {
        try {
            String log = port.clearLog();
            GenericEntity<String> entity = new GenericEntity<String>(log) {};

            return Response.status(Response.Status.OK)
                    .entity(entity)
                    .build();
        } catch(Exception re) {
            return Response.status(Response.Status.NOT_IMPLEMENTED)
                    .entity(re.getMessage())
                    .build();
        }
    }
}
