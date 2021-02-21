package factories;

import repository.core.IAlbumManager;

import utilities.ConfigReader;

import java.lang.reflect.Constructor;

public class AlbumManagerFactory {
    private static String CONFIG_FILE = "config.json";
    private static String CONFIG_KEY = "AlbumManagerImpl";

    private static IAlbumManager albumManager;

    public static IAlbumManager loadManager() {
        try {
            synchronized (IAlbumManager.class) {
                if (albumManager == null) {
                    Class<?> cl = Class.forName(ConfigReader.getConfigFileKey(CONFIG_FILE, CONFIG_KEY));
                    Constructor<?> cons = cl.getConstructor();

                    albumManager = (IAlbumManager) cons.newInstance();
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return albumManager;
    }

    // TO REMOVE: quick testing
//    public static void main(String[] args) {
//        IAlbumManager albumManager = AlbumManagerFactory.loadManager();
//
//        // check albumManager is defined
//        System.out.println("Defined: " + (albumManager != null ? true : false));
//
//        Album a1 = new Album("isrc1", "title1", 2021, "artist1", "contentDesc1");
//        Album a2 = new Album("isrc2", "title2", 2022, "artist2", "contentDesc2");
//        Album a3 = new Album("isrc3", "title3", 2023, "artist3", "contentDesc3");
//        Album duplicate = new Album("isrc1", "dupTitl", 2020, "dupArtist", "dupContentDesc");
//
//        // Empty list
//        System.out.println("Empty: " + albumManager.listAlbum());
//
//        // Add album
//        System.out.println("Added: " + albumManager.addAlbum(a1));
//        System.out.println("Added: " + albumManager.addAlbum(a2));
//        System.out.println("Added: " + albumManager.addAlbum(a3));
//        System.out.println("Adding duplicate: " + albumManager.addAlbum(duplicate));
//        System.out.println(3 + " : " + albumManager.listAlbum().size());
//
//        // Delete album
//        System.out.println("Deleted: " + albumManager.deleteAlbum(a3.getIsrc()));
//        System.out.println(2 + " : " + albumManager.listAlbum().size());
//
//        // Update album
//        a1.setTitle("updatedTitle1");
//        System.out.println("Updated: " + albumManager.updateAlbum(a1));
//        System.out.println(2 + " : " + albumManager.listAlbum().size());
//        System.out.println("Updated field: " + albumManager.getAlbum(a1.getIsrc()));
//    }
}
