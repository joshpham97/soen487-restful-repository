package repository.business;

import repository.core.IAlbumManager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;

public class AlbumManagerFactory {
    private static String CONFIG_FILE = "config.json";
    private static String CONFIG_KEY = "AlbumManagerImpl";

    private static IAlbumManager albumManager;

    public static IAlbumManager loadManager() {
        try {
            if (albumManager == null) {
                synchronized (IAlbumManager.class) {
                    Class<?> cl = Class.forName(getConfigFileKey());
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

    public static String getConfigFileKey() throws IOException, ParseException {
        InputStream inputStream = AlbumManagerFactory.class.getClassLoader().getResourceAsStream(CONFIG_FILE); // Get resource
        Object obj = new JSONParser().parse(new InputStreamReader(inputStream)); // Read file
        JSONObject jo = (JSONObject) obj;

        return jo.get(CONFIG_KEY).toString();
    }
}
