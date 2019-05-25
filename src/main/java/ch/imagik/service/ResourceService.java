package ch.imagik.service;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceService {
    private Locale locale;

    public Locale setLanguage(String language) {
        switch (language == null ? "" : language) {
            case "it":
                locale = Locale.ITALY;
                break;
            case "en":
                locale = Locale.ENGLISH;
                break;
            default:
                locale = Locale.getDefault();
        }
        Locale.setDefault(locale);
        return locale;
    }

    public String getLocalizedString(String key) {
        return getBundle().getString(key);
    }

    public ResourceBundle getBundle() {
        return ResourceBundle.getBundle("ch/imagik/bundles/imagik", locale);
    }
}
