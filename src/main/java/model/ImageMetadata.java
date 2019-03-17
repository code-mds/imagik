package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ImageMetadata {
    private final StringProperty type;
    private final StringProperty name;
    private final StringProperty value;

    public ImageMetadata() { this(null, null, null); }
    public ImageMetadata(String type, String name, String value) {
        this.type = new SimpleStringProperty(type);
        this.name = new SimpleStringProperty(name);
        this.value = new SimpleStringProperty(value);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }
}
