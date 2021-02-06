package client;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.commons.io.IOUtils;


public class ArtistClient {
    private static String resourceBase;

    public ArtistClient(){ }

    private String getResourceBase(){
        if (resourceBase == null){
            try
            {
                InputStream resourcePath = getClass().getClassLoader().getResourceAsStream("rest_apis.json");
                String apiResource = IOUtils.toString(resourcePath, StandardCharsets.UTF_8);
                JSONParser jsonParser = new JSONParser();

                //Read JSON file
                JSONObject resource = (JSONObject) jsonParser.parse(apiResource);
                resourceBase = (String) resource.get("artist");
            } catch (Exception e) {
                System.out.println("There was an error reading the api path.");
                e.printStackTrace();
                return null;
            }
        }

        return resourceBase;
    }

    public String list(){
        try{
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(getResourceBase());

            HttpResponse response = client.execute(request);
            return readResponse(response);
        }catch (HttpHostConnectException e){
            return "There is an error connecting to the server.";
        }
        catch (Exception e) {
            return "There is an error getting a response from the server.";
        }
    }

    public String get(String artistNickname){
        try{
            String queryString = String.format("?nickname=%s", encodeValue(artistNickname));

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(getResourceBase() + queryString);

            HttpResponse response = client.execute(request);
            return readResponse(response);
        }catch (HttpHostConnectException e){
            return "There is an error connecting to the server.";
        }catch (Exception e) {
            return "There is an error getting a response from the server.";
        }
    }

    public String post(String nickname, String firstname, String lastname, String bio){
        try{
            UrlEncodedFormEntity urlEncodedFormEntity = getAttribute(nickname, firstname, lastname, bio);

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(getResourceBase());

            post.setEntity(urlEncodedFormEntity);

            HttpResponse response = client.execute(post);
            return readResponse(response);
        }catch (UnsupportedEncodingException e){
            return "There is an error getting the input.";
        }catch (HttpHostConnectException e){
            return "There is an error connecting to the server.";
        }catch (Exception e) {
            return "There is an error getting a response from the server.";
        }
    }

    public String put(String nickname, String firstname, String lastname, String bio){
        try{
            UrlEncodedFormEntity urlEncodedFormEntity = getAttribute(nickname, firstname, lastname, bio);

            HttpClient client = new DefaultHttpClient();
            HttpPut post = new HttpPut(getResourceBase());

            post.setEntity(urlEncodedFormEntity);

            HttpResponse response = client.execute(post);
            return readResponse(response);
        }catch (UnsupportedEncodingException e){
            return "There is an error getting the input.";
        }catch (HttpHostConnectException e){
            return "There is an error connecting to the server.";
        }catch (Exception e) {
            return "There is an error getting a response from the server.";
        }
    }

    public String delete(String nickname){
        try{
            String queryString = String.format("?nickname=%s", encodeValue(nickname));

            HttpClient client = new DefaultHttpClient();
            HttpDelete request = new HttpDelete(getResourceBase() + queryString);

            HttpResponse response = client.execute(request);
            return readResponse(response);
        }catch (Exception e) {
            return "There is an error getting a response from the server.";
        }
    }

    public UrlEncodedFormEntity getAttribute(String nickname, String firstname, String lastname, String bio) throws UnsupportedEncodingException {
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("nickname", encodeValue(nickname)));
        urlParameters.add(new BasicNameValuePair("firstname", encodeValue(firstname)));
        urlParameters.add(new BasicNameValuePair("lastname", encodeValue(lastname)));
        urlParameters.add(new BasicNameValuePair("bio", encodeValue(bio)));

        return new UrlEncodedFormEntity(urlParameters);
    }

    public String readResponse(HttpResponse response){
        try{
            System.out.println("Response Code: " + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null)
                result.append(line);

            return result.toString();
        }catch (Exception e) {
            System.out.println("There was an error reading the response.");
            e.printStackTrace();
        }

        return null;
    }

    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }
}
