package factories;

import repository.core.IArtistManager;
import utilities.ConfigReader;
import java.lang.reflect.Constructor;

public class ArtistManagerFactory {
    private static String CONFIG_FILE = "config.json";
    private static String CONFIG_KEY = "ArtistManagerImpl";

    private static IArtistManager artistManager;

    public static IArtistManager loadManager() {
        try {
            synchronized (IArtistManager.class) {
                 if (artistManager == null) {
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
        IArtistManager artistManager = ArtistManagerFactory.loadManager();

        // check artisManager is defined
        System.out.println("Defined: " + (artistManager != null ? true : false));

        Artist a1 = new Artist("stef", "stefan", "jb", "guitarist");
        Artist a2 = new Artist("coolBob", "bob", "bobby", "singer");
        Artist a3 = new Artist("Guy", "Guy", "Dude", "artist3");
        Artist duplicate = new Artist("stef", "dupTitl", "2020", "dupArtist");

        // Empty list
        System.out.println("Empty: " + artistManager.listArtist());

        // Add artist
        System.out.println("Added: " + artistManager.addArtist(a1));
        System.out.println("Added: " + artistManager.addArtist(a2));
        System.out.println("Added: " + artistManager.addArtist(a3));
        System.out.println("Adding duplicate: " + artistManager.addArtist(duplicate));
        System.out.println(3 + " : " + artistManager.listArtist().size());

        // Delete artist
        System.out.println("Deleted: " + artistManager.deleteArtist(a3.getNickname()));
        System.out.println(2 + " : " + artistManager.listArtist().size());

        // Update artist
        a1.setFirstname("updatedTitle1");
        System.out.println("Updated: " + artistManager.updateArtist(a1));
        System.out.println(2 + " : " + artistManager.listArtist().size());
        System.out.println("Updated field: " + artistManager.getArtist(a1.getNickname()));
    }*/
}
