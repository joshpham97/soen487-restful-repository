package client;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ArtistClient {
    private static String resourceBase;

    private static String getResourceBase(){
        if (resourceBase == null){
            String result = "An unknown error has occurred.";
            JSONParser jsonParser = new JSONParser();

            try (FileReader reader = new FileReader("resource.json"))
            {
                //Read JSON file
                JSONObject resource = (JSONObject) jsonParser.parse(reader);
                JSONObject repositories = (JSONObject) resource.get("repositories");
                resourceBase = ((String) resource.get("base")) + ((String) repositories.get("artist"));
            } catch (Exception e) {
                System.out.println("There was an error reading the api path.");
                e.printStackTrace();
                return null;
            }
        }

        return resourceBase;
    }

    public static void list(){
        try{
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(getResourceBase());

            HttpResponse response = client.execute(request);
            readResponse(response);
        }catch (Exception e) {
            System.out.println("There is an error getting a response from the server.");
            e.printStackTrace();
        }
    }

    public static void get(){
        try{
            Scanner scanner = new Scanner(System.in);

            System.out.print("Please enter the nickname of the artist that you would like to view: ");
            String artistNickname = scanner.nextLine();
            String queryString = String.format("?nickname=%s", artistNickname);

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(getResourceBase() + queryString);

            HttpResponse response = client.execute(request);
            readResponse(response);
        }catch (Exception e) {
            System.out.println("There is an error getting a response from the server.");
            e.printStackTrace();
        }
    }

    public static void post(){
        try{
            UrlEncodedFormEntity urlEncodedFormEntity = getAttribute();

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(getResourceBase());

            post.setEntity(urlEncodedFormEntity);

            HttpResponse response = client.execute(post);
            readResponse(response);
        }catch (UnsupportedEncodingException e){
            System.out.println("There is an error getting the input.");
            e.printStackTrace();
        }catch (Exception e) {
            System.out.println("There is an error getting a response from the server.");
            e.printStackTrace();
        }
    }

    public static void put(){
        try{
            UrlEncodedFormEntity urlEncodedFormEntity = getAttribute();

            HttpClient client = new DefaultHttpClient();
            HttpPut post = new HttpPut(getResourceBase());

            post.setEntity(urlEncodedFormEntity);

            HttpResponse response = client.execute(post);
            readResponse(response);
        }catch (UnsupportedEncodingException e){
            System.out.println("There is an error getting the input.");
            e.printStackTrace();
        }catch (Exception e) {
            System.out.println("There is an error getting a response from the server.");
            e.printStackTrace();
        }
    }

    public static void delete(){
        try{
            Scanner scanner = new Scanner(System.in);

            System.out.print("Please enter the nickname of the artist that you would like to view: ");
            String artistNickname = scanner.nextLine();
            String queryString = String.format("?nickname=%s", artistNickname);

            HttpClient client = new DefaultHttpClient();
            HttpDelete request = new HttpDelete(getResourceBase() + queryString);

            HttpResponse response = client.execute(request);
            readResponse(response);
        }catch (Exception e) {
            System.out.println("There is an error getting a response from the server.");
            e.printStackTrace();
        }
    }

    public static UrlEncodedFormEntity getAttribute() throws UnsupportedEncodingException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the information of the artist: ");

        System.out.print("Artist nickname: ");
        String nickname = scanner.nextLine();

        System.out.print("First name: ");
        String firstname = scanner.nextLine();

        System.out.print("Last name: ");
        String lastname = scanner.nextLine();

        System.out.print("Bio: ");
        String bio = scanner.nextLine();

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("nickname", nickname));
        urlParameters.add(new BasicNameValuePair("firstname", firstname));
        urlParameters.add(new BasicNameValuePair("lastname", lastname));
        urlParameters.add(new BasicNameValuePair("bio", bio));

        return new UrlEncodedFormEntity(urlParameters);
    }

    public static void readResponse(HttpResponse response){
        try{
            System.out.println("Response Code: " + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null)
                result.append(line);
            System.out.println(result.toString());
        }catch (Exception e) {
            System.out.println("There was an error reading the response.");
            e.printStackTrace();
        }
    }
}
