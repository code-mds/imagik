package ch.imagik.service;

import ch.imagik.event.EventManager;
import ch.imagik.event.FolderSelectedEvent;
import ch.imagik.model.Folder;

import java.io.*;
import java.util.Properties;

public class ConfigService {
    public static String KEY_LAST_FOLDER = "last_folder";
    public static String KEY_LOG = "log";

    private String userHome = System.getProperty("user.home");
    private String imagikBaseFolder = userHome + "/.imagik/";
    private String imagikConfig = imagikBaseFolder + "imagik.properties";
    private String imagikLog = imagikBaseFolder + "imagik.log";
    private Properties properties = new Properties();

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
