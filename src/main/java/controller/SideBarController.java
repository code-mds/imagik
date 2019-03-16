package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {
    @FXML private MetadataController metadataController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InputStream input = getClass().getResourceAsStream("/test.jpg");
        metadataController.loadMetadata(input);
    }
}
