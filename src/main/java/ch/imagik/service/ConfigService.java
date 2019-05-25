package ch.imagik.service;

import java.io.*;
import java.util.Properties;

public class ConfigService {
    public static final String KEY_LAST_FOLDER = "last_folder";
    public static final String KEY_LOG = "log";
    public static final String KEY_LANGUAGE = "language";

    private final String userHome = System.getProperty("user.home");
    private final String imagikBaseFolder = userHome + "/.imagik/";
    private final String imagikConfig = imagikBaseFolder + "imagik.properties";
    private final String imagikLog = imagikBaseFolder + "imagik.log";
    private final Properties properties = new Properties();

    public ConfigService() {
        initHomeFolder();
    }

    private void initHomeFolder() {
        File path = new File(imagikBaseFolder);
        if(!path.exists()) {
            path.mkdirs();
        }

        path = new File(imagikConfig);
        if(!path.exists()) {
            try {
                path.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (InputStream input = new FileInputStream(imagikConfig)) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if(getConfigEntry(KEY_LOG) == null)
            setConfigEntry(KEY_LOG, imagikLog);
    }

    public String getConfigEntry(String key) {
        return properties.getProperty(key);
    }

    public void setConfigEntry(String key, String value) {
        try (OutputStream output = new FileOutputStream(imagikConfig)) {
            properties.setProperty(key, value);
            properties.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
