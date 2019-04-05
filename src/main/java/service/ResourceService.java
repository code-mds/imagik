package service;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceService {
    private final Locale locale = Locale.getDefault();
    private final ResourceBundle bundle = ResourceBundle.getBundle("bundles/imagik", locale);

    public String getLocalizedString(String key) {
        return bundle.getString(key);
    }
}
