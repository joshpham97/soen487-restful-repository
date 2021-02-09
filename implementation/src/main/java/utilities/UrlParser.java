package utilities;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class UrlParser {
    public static Map<String, String> parseStrParams(String strParams) throws UnsupportedEncodingException {
        Map<String, String> map = new LinkedHashMap<>();

        String[] pair;
        for(String param : strParams.split("&")) {
            pair = param.split("=");
            map.put(pair[0], pair.length == 1 ? null : URLDecoder.decode(pair[1], "UTF-8"));
        }

        return map;
    }
}
