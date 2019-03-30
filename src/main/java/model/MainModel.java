package model;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class MainModel {
    // single instance
    private static MainModel instance;

    private ImageService imageService = ImageService.build();
    private ResourceService resourceService = new ResourceService();

    private BooleanProperty disableZoom = new SimpleBooleanProperty(true);
    private BooleanProperty disableEdit = new SimpleBooleanProperty(true);
    private BooleanProperty showEditPane = new SimpleBooleanProperty(false);

    private StringProperty filterProperty = new SimpleStringProperty();
    private ObjectProperty<File> selectedFolderProperty = new SimpleObjectProperty<>();
    private ObservableList<File> selectedFiles = FXCollections.observableArrayList();

    private MainModel() { }

    public static MainModel getInstance() {
        if(instance == null)
            instance = new MainModel();

        return instance;
    }

    public void addSelectedFolderListener(ChangeListener<File> listener) {
        instance.selectedFolderProperty.addListener(listener);
    }

    public void addFilterListener(ChangeListener<String> listener) {
        instance.filterProperty.addListener(listener);
    }

    public void setSelectedFolder(File file) {
        selectedFolderProperty.set(file);
    }

    public void setFilter(String filter) {
        filterProperty.setValue(filter);
    }

    public ImageService getImageService() {
        return imageService;
    }

    public void setSelectedFiles(ObservableList<? extends File> list) {
        selectedFiles.setAll(list);
    }

    public ObservableList<File> getSelectedFiles() {
        return selectedFiles;
    }

    public BooleanProperty disableZoomProperty() {
        return disableZoom;
    }
    public BooleanProperty disableEditProperty() {
        return disableEdit;
    }

    public BooleanProperty showEditPaneProperty() {
        return showEditPane;
    }

    public String getLocalizedString(String key) {
        return resourceService.getLocalizedString(key);
    }
}
