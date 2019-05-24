package ch.imagik.controller;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import ch.imagik.model.ImageMetadata;
import ch.imagik.model.MainModel;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MetadataController implements Initializable {
    @FXML private TableView<ImageMetadata> tableView;
    @FXML private Label fileName;
    private final ObservableList<ImageMetadata> imageMetadataList = FXCollections.observableArrayList();

    public void initialize(URL location, ResourceBundle resources) {
        tableView.setItems(imageMetadataList);
        MainModel.getInstance().getSelectedFiles()
                .addListener((ListChangeListener.Change<? extends File> l)-> loadMetadata( MainModel.getInstance().getSelectedFiles()));
    }

    private void loadMetadata(List<File> inputList) {
        try {
            String name = "";
            imageMetadataList.clear();

            // show metadata only if a single file is selected
            // otherwise clear only the metadata pane
            if(inputList.size() == 1) {
                File file = inputList.get(0);
                if(file != null) {
                    name = file.getName();
                    Metadata metadata = ImageMetadataReader.readMetadata(file);
                    updateMetadataList(metadata);
                }
            }
            fileName.textProperty().setValue(name);
        } catch (ImageProcessingException | IOException e) {
            e.printStackTrace();
        }
    }

    private void updateMetadataList(Metadata metadata) {
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                ImageMetadata imageMetadata = new ImageMetadata(tag.getDirectoryName(), tag.getTagName(), tag.getDescription());
                imageMetadataList.add(imageMetadata);
            }
        }
    }
}
