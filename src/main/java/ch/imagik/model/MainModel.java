package ch.imagik.model;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ch.imagik.service.ImageService;
import ch.imagik.service.ResourceService;

import java.io.File;

public class MainModel {
    // single instance
    private static MainModel instance;

    private final ImageService imageService = ImageService.build();
    private final ResourceService resourceService = new ResourceService();

    private final BooleanProperty disableZoom = new SimpleBooleanProperty(true);
    private final BooleanProperty disableEdit = new SimpleBooleanProperty(true);

    private final BooleanProperty showEditPane = new SimpleBooleanProperty(false);
    private final BooleanProperty showMetadata = new SimpleBooleanProperty(false);

    private final StringProperty filterProperty = new SimpleStringProperty();
    private final ObjectProperty<File> selectedFolderProperty = new SimpleObjectProperty<>();
    private final ObservableList<File> selectedFiles = FXCollections.observableArrayList();

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
    public BooleanProperty showMetadataProperty() {
        return showMetadata;
    }

    public String getLocalizedString(String key) {
        return resourceService.getLocalizedString(key);
    }
}
