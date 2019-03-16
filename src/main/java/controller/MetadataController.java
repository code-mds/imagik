package controller;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class MetadataController implements Initializable {
    @FXML
    private GridPane gridPane;

    public MetadataController()  {
        System.out.println("CTOR");
    }

    public void loadMetadata(InputStream input) {
        try {
            gridPane.getChildren().clear();

            int rowIndex = 0;

            Metadata metadata = ImageMetadataReader.readMetadata(input);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    System.out.println(tag);

                    final Label labelGroup = new Label(tag.getDirectoryName());
                    gridPane.add(labelGroup,0, rowIndex);

                    final Label label = new Label(tag.getTagName());
                    gridPane.add(label,1, rowIndex);

                    final TextField textField = new TextField(tag.getDescription());
                    gridPane.add(textField, 2, rowIndex);
                    rowIndex++;
                }
            }

            gridPane.requestLayout();
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("INIT");
    }
}
