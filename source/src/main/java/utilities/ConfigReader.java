package utilities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//import repository.business.AlbumManagerFactory;
import repository.business.ArtistManagerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigReader {
    public static String getConfigFileKey(String file, String key) throws IOException, ParseException  {
        InputStream inputStream = ArtistManagerFactory.class.getClassLoader().getResourceAsStream(file); // Get resource
        Object obj = new JSONParser().parse(new InputStreamReader(inputStream)); // Read file
        JSONObject jo = (JSONObject) obj;

        inputStream.close();
        return jo.get(key).toString();
    }
}
