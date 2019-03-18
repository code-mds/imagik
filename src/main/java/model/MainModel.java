package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

import java.io.File;

public class MainModel {
    private ObjectProperty<File> selectedFileProperty = new SimpleObjectProperty<>();
    private static MainModel instance;

    private MainModel() {

    }

    public static MainModel getInstance() {
        if(instance == null)
            instance = new MainModel();

        return instance;
    }

    public void addListener(ChangeListener<File> listener) {
        instance.selectedFileProperty.addListener(listener);
    }

    public void removeListener(ChangeListener<File> listener) {
        instance.selectedFileProperty.removeListener(listener);
    }

    public void setSelectedFile(File file) {
        selectedFileProperty.set(file);
    }

    public File getSelectedFile() {
        return selectedFileProperty.get();
    }

}
