package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;

import java.io.File;

public class MainModel {
    // single instance
    private static MainModel instance;

    private StringProperty filterProperty = new SimpleStringProperty();
    private ObjectProperty<File> selectedFolderProperty = new SimpleObjectProperty<>();
    private ObjectProperty<File> selectedFileProperty = new SimpleObjectProperty<>();


    private MainModel() { }

    public static MainModel getInstance() {
        if(instance == null)
            instance = new MainModel();

        return instance;
    }

    public void addSelectedFileListener(ChangeListener<File> listener) {
        instance.selectedFileProperty.addListener(listener);
    }

    public void addSelectedFolderListener(ChangeListener<File> listener) {
        instance.selectedFolderProperty.addListener(listener);
    }

    public void addFilterListener(ChangeListener<String> listener) {
        instance.filterProperty.addListener(listener);
    }

    public void setSelectedFile(File file) {
        selectedFileProperty.set(file);
    }

    public void setSelectedFolder(File file) {
        selectedFolderProperty.set(file);
    }

    public void setFilter(String filter) {
        filterProperty.setValue(filter);
    }
}
