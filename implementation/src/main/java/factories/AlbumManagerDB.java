package factories;

import repository.core.IAlbumManager;
import utilities.ConfigReader;

import java.lang.reflect.Constructor;

public class AlbumManagerDB {
    private static String CONFIG_FILE = "config.json";
    private static String CONFIG_KEY = "AlbumManagerDB";

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
}
