package factories;

import repository.core.ILogManager;
import utilities.ConfigReader;

import java.lang.reflect.Constructor;

public class LogManagerFactory {
    private static String CONFIG_FILE = "config.json";
    private static String CONFIG_KEY = "LogManagerImpl";

    private static ILogManager logManager;

    public static ILogManager loadManager() {
        try {
            synchronized (ILogManager.class) {
                if (logManager == null) {
                    Class<?> cl = Class.forName(ConfigReader.getConfigFileKey(CONFIG_FILE, CONFIG_KEY));
                    Constructor<?> cons = cl.getConstructor();

                    logManager = (ILogManager) cons.newInstance();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return logManager;
    }
}
