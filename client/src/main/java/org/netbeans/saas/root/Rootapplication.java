/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.saas.root;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.netbeans.saas.RestConnection;
import org.netbeans.saas.RestResponse;

/**
 * Rootapplication Service
 *
 * @author thuan
 */
public class Rootapplication {

    /**
     * Creates a new instance of Rootapplication
     */
    public Rootapplication() {
    }
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Throwable th) {
        }
    }

    /**
     *
     * @return an instance of RestResponse
     */
    public static RestResponse listAlbum() throws IOException {
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{};
        RestConnection conn = new RestConnection("http://localhost:8081/myapp/album", pathParams, queryParams);
        sleep(1000);
        return conn.get(null);
    }

    /**
     *
     * @param isrc
     * @param title
     * @param releaseYear
     * @param artist
     * @param contentDesc
     * @return an instance of RestResponse
     */
    public static RestResponse addAlbum(String isrc, String title, Integer releaseYear, String artist, String contentDesc) throws IOException {
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{{"isrc", isrc}, {"title", title}, {"releaseYear", releaseYear.toString()}, {"artist", artist}, {"contentDesc", contentDesc}};
        RestConnection conn = new RestConnection("http://localhost:8081/myapp/album", pathParams, null);
        sleep(1000);
        return conn.post(null, queryParams);
    }

    /**
     *
     * @return an instance of RestResponse
     */
    public static RestResponse getAlbum(String irsc) throws IOException {
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{};
        RestConnection conn = new RestConnection(String.format("http://localhost:8081/myapp/album/%s", irsc), pathParams, queryParams);
        sleep(1000);
        return conn.get(null);
    }

    /**
     *
     * @param isrc
     * @param title
     * @param releaseYear
     * @param artist
     * @param contentDesc
     * @return an instance of RestResponse
     */
    public static RestResponse updateAlbum(String isrc, String title, Integer releaseYear, String artist, String contentDesc) throws IOException {
        String[][] pathParams = new String[][]{{"Content-Type", "text/plain"}};

        //JSONObject album = new JSONObject();
        //album.put("isrc", isrc);
        //album.put("title", title);
        //album.put("releaseYear", releaseYear);
        //album.put("contentDesc", contentDesc);
        String[][] queryParams = new String[][]{{"isrc", isrc}, {"title", title}, {"releaseYear", releaseYear.toString()}, {"artist", artist}, {"contentDesc", contentDesc}};


        RestConnection conn = new RestConnection("http://localhost:8081/myapp/album", pathParams, null);
        sleep(1000);
        return conn.put(null, queryParams);
    }

    /**
     *
     * @return an instance of RestResponse
     */
    public static RestResponse deleteAlbum(String irsc) throws IOException {
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{};
        RestConnection conn = new RestConnection(String.format("http://localhost:8081/myapp/album/%s", irsc), pathParams, queryParams);
        sleep(1000);
        return conn.delete(null);
    }
}
