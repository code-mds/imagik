package ch.imagik.service;

import java.io.*;
import java.util.Properties;

public class ConfigService {
    public static final String KEY_LAST_FOLDER = "last_folder";
    public static final String KEY_LOG = "log";

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

        if(getEntry(KEY_LOG) == null)
            setEntry(KEY_LOG, imagikLog);
    }

    public String getEntry(String key) {
        return properties.getProperty(key);
    }

    public void setEntry(String key, String value) {
        try (OutputStream output = new FileOutputStream(imagikConfig)) {
            properties.setProperty(key, value);
            properties.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
