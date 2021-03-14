package com.example.proxy;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Scanner;

@Path("log")
public class LogREST {
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response soapOperation(String xml) {
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(String.format("http://localhost:8090/log"));

            httpPost.setHeader("Content-Type", "text/xml");
            StringEntity entity = new StringEntity(xml);
            httpPost.setEntity(entity);

            CloseableHttpResponse response = client.execute(httpPost);

            return Response.status(Response.Status.OK)
                    .entity(readResponse(response))
                    .build();
        } catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    private static String readResponse(CloseableHttpResponse response) throws IOException {
        Scanner sc = new Scanner(response.getEntity().getContent());

        StringBuilder stringResponse = new StringBuilder();
        while (sc.hasNext())
            stringResponse.append(sc.nextLine() + "\n");

        response.close();
        return stringResponse.toString();
    }
}
