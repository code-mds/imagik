package model;

import com.google.common.eventbus.EventBus;
import controller.Subscriber;
import controller.event.PostableEvent;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;

import java.io.File;

public class MainModel {
    // single instance
    private static MainModel instance;

    private ObjectProperty<File> selectedFileProperty = new SimpleObjectProperty<>();
    private EventBus eventBus = new EventBus();


    private MainModel() { }

    public static MainModel getInstance() {
        if(instance == null)
            instance = new MainModel();

        return instance;
    }

    public void addSelectedFileListener(ChangeListener<File> listener) {
        instance.selectedFileProperty.addListener(listener);
    }
    public void removeSelectedFileListener(ChangeListener<File> listener) {  instance.selectedFileProperty.removeListener(listener); }

    public void setSelectedFile(File file) {
        selectedFileProperty.set(file);
    }

    public File getSelectedFile() {
        return selectedFileProperty.get();
    }

    public void register(Subscriber subscriber) {
        eventBus.register(subscriber);
    }

    public void post(PostableEvent event) {
        eventBus.post(event);
    }
}
