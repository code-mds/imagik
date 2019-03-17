package controller;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import model.ImageMetadata;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class MetadataController implements Initializable {
    @FXML
    private TableView<ImageMetadata> tableView;

    private final ObservableList<ImageMetadata> imageMetadataList = FXCollections.observableArrayList();

    public void loadMetadata(InputStream input) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(input);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    //System.out.println(tag);

                    ImageMetadata imageMetadata = new ImageMetadata(tag.getDirectoryName(), tag.getTagName(), tag.getDescription());
                    imageMetadataList.add(imageMetadata);
                }
            }
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        tableView.setItems(imageMetadataList);
        System.out.println("MetadataController.INIT");
    }

    public void selectDirectory(ActionEvent actionEvent) {
    }
}
