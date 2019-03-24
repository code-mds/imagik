package controller;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import model.ImageMetadata;
import model.MainModel;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MetadataController implements Initializable {
    @FXML private TableView<ImageMetadata> tableView;
    @FXML private Label fileName;
    private final ObservableList<ImageMetadata> imageMetadataList = FXCollections.observableArrayList();

    public void initialize(URL location, ResourceBundle resources) {
        tableView.setItems(imageMetadataList);
        MainModel.getInstance().addSelectedFileListener((observable, oldValue, newValue) -> loadMetadata(newValue));
    }

    private void loadMetadata(File input) {
        try {
            imageMetadataList.clear();

            String name = "";
            if(input != null) {
                name = input.getName();
                Metadata metadata = ImageMetadataReader.readMetadata(input);
                updateMetadataList(metadata);
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
