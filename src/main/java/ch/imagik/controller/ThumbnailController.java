package ch.imagik.controller;

import ch.imagik.event.*;
import ch.imagik.model.Folder;
import com.google.common.eventbus.Subscribe;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ch.imagik.service.ImageService;
import ch.imagik.model.MainModel;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.util.*;

public class ThumbnailController implements Initializable, EventSubscriber {
    private final static int THUMB_SIZE = 50;
    @FXML private ListView<File> thumbListView;
    private final ObservableList<File> imageList = FXCollections.observableArrayList();
    private final FilteredList<File> filteredImageList = new FilteredList<>(imageList);
    private final DateFormat dateFormatter = DateFormat.getDateTimeInstance(
                    DateFormat.SHORT,
                    DateFormat.SHORT,
                    Locale.getDefault());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventManager.getInstance().register(this);

        MainModel mainModel = MainModel.getInstance();

        mainModel.addFilterListener((observable, oldValue, newValue) -> filterList(newValue));
        ImageService imageService = mainModel.getImageService();
        thumbListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        thumbListView.setItems(filteredImageList);
        thumbListView.getSelectionModel()
                .getSelectedItems()
                .addListener((ListChangeListener.Change<? extends File> l) -> MainModel.getInstance().setSelectedFiles(l.getList()));

        thumbListView.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            public void updateItem(File file, boolean empty) {
                super.updateItem(file, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.fitHeightProperty().setValue(THUMB_SIZE);
                    imageView.fitWidthProperty().setValue(THUMB_SIZE);
                    imageView.setImage(imageService.getThumbnail(file, THUMB_SIZE));

                    String dateModified = dateFormatter.format(new Date(file.lastModified()));
                    String text = String.format("%s\n%s\n%s",
                            file.getName(),
                            dateModified,
                            humanReadableByteCount(file.length(), true));

                    setText(text);
                    setGraphic(imageView);
                }
            }
        });
    }

    private static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    private void filterList(String filter) {
        filteredImageList.setPredicate(file -> file.getName().toLowerCase().contains(filter));
    }

    private void populateImageList(Folder dir) {
        File[] files = ImageService.listImages(dir);
        imageList.setAll(files);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void folderRefresh(FolderRefreshEvent e) {
        File[] files = ImageService.listImages(e.getFolder());
        for (File f : files) {
            if(!imageList.contains(f))
                imageList.add(f);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void folderSelected(FolderSelectedEvent e) {
        populateImageList(e.getFolder());
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void fileChanged(FilesChangedEvent e) {
        for (File file : e.getFiles()) {
            int oldPos = imageList.indexOf(file);
            imageList.set(oldPos, file);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void brokenImageHandler(BrokenImageEvent e) {
        imageList.remove(e.getFile());
    }
}
