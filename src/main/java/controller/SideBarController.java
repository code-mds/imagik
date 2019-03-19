package controller;

import com.google.common.eventbus.Subscribe;
import controller.event.SelectFolderEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import model.MainModel;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable, Subscriber {
    @FXML private AnchorPane ap;

    @FXML private ThumbnailController thumbnailController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainModel.getInstance().register(this);
    }

    public void selectDirectory(ActionEvent actionEvent) {
        selectDirectory(new SelectFolderEvent());
    }

    @Subscribe
    public void selectDirectory(SelectFolderEvent event) {
        Window window = ap.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File dir = directoryChooser.showDialog(window);
        if(dir == null || !dir.isDirectory())
            return;

        thumbnailController.update(dir);
    }

}
