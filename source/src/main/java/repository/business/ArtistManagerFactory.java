package repository.business;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONObject;
import repository.core.Artist;
import repository.core.IArtistManager;
import utilities.ConfigReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.text.ParseException;

public class ArtistManagerFactory {
    private static String CONFIG_FILE = "config.json";
    private static String CONFIG_KEY = "ArtistManagerImpl";

    private static IArtistManager artistManager;

    public static IArtistManager loadManager() {
        try {
            if (artistManager == null) {
                synchronized (IArtistManager.class) {
                    Class<?> cl = Class.forName(ConfigReader.getConfigFileKey(CONFIG_FILE, CONFIG_KEY));
                    Constructor<?> cons = cl.getConstructor();

                    artistManager = (IArtistManager) cons.newInstance();
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return artistManager;
    }

    //TESTING CRUD
   /* public static void main(String[] args) {
        IArtistManager albumManager = ArtistManagerFactory.loadManager();

        // check albumManager is defined
        System.out.println("Defined: " + (albumManager != null ? true : false));

        Artist a1 = new Artist("isrc1", "title1", "2021", "artist1");
        Artist a2 = new Artist("isrc2", "title2", "2022", "artist2");
        Artist a3 = new Artist("isrc3", "title3", "2023", "artist3");
        Artist duplicate = new Artist("isrc1", "dupTitl", "2020", "dupArtist");

        // Empty list
        System.out.println("Empty: " + albumManager.listArtist());

        // Add album
        System.out.println("Added: " + albumManager.addArtist(a1));
        System.out.println("Added: " + albumManager.addArtist(a2));
        System.out.println("Added: " + albumManager.addArtist(a3));
        System.out.println("Adding duplicate: " + albumManager.addArtist(duplicate));
        System.out.println(3 + " : " + albumManager.listArtist().size());

        // Delete album
        System.out.println("Deleted: " + albumManager.deleteArtist(a3.getNickname()));
        System.out.println(2 + " : " + albumManager.listArtist().size());

        // Update album
        a1.setFirstname("updatedTitle1");
        System.out.println("Updated: " + albumManager.updateArtist(a1));
        System.out.println(2 + " : " + albumManager.listArtist().size());
        System.out.println("Updated field: " + albumManager.getArtist(a1.getNickname()));
    }*/
}
