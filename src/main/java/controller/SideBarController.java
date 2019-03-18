package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {
    @FXML private ThumbnailController thumbnailController;
    @FXML private MetadataController metadataController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        InputStream input = getClass().getResourceAsStream("/test.jpg");
//        metadataController.loadMetadata(input);
    }

    /*
    The user has selected a directory
     */
    public void selectDirectory(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Scene scene = ((Node)actionEvent.getSource()).getScene();
        File dir = directoryChooser.showDialog(scene.getWindow());
        if(dir == null || !dir.isDirectory())
            return;

        thumbnailController.update(dir);
    }
}
