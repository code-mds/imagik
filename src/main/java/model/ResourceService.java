package model;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceService {
    Locale locale = Locale.getDefault();
    ResourceBundle bundle = ResourceBundle.getBundle("bundles/imagik", locale);

    public String getLocalizedString(String key) {
        return bundle.getString(key);
    }
}
