package utilities;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionParser {
    public static String getStackTraceString(Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));

        return stringWriter.toString();
    }
}
