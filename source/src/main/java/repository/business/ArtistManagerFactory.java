package repository.business;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONObject;
import repository.core.IArtistManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.text.ParseException;

public class ArtistManagerFactory {
    private static String CONFIG_FILE = "config.json";
    private static String CONFIG_KEY = "AlbumManagerImpl";

    private static IArtistManager artistManager;

    public static IArtistManager loadManager() {
        try {
            if (artistManager == null) {
                synchronized (IArtistManager.class) {
                    Class<?> cl = Class.forName(getConfigFileKey());
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

    public static String getConfigFileKey() throws IOException, ParseException {
        InputStream inputStream = ArtistManagerFactory.class.getClassLoader().getResourceAsStream(CONFIG_FILE); // Get resource
        Object obj = new JSONParser().parse(new InputStreamReader(inputStream)); // Read file
        JSONObject jo = (JSONObject) obj;

        return jo.get(CONFIG_KEY).toString();
    }
}
