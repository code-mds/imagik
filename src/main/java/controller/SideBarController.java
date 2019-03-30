package controller;

import com.google.common.eventbus.Subscribe;
import event.EventManager;
import event.FolderSelectedEvent;
import event.SelectFolderEvent;
import event.EventSubscriber;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import model.MainModel;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable, EventSubscriber {
    @FXML public AnchorPane metadataPane;
    @FXML public SplitPane splitPane;
    @FXML private TextField searchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventManager.getInstance().register(this);
        searchField.textProperty().addListener((obs, old, newVal) -> MainModel.getInstance().setFilter(newVal));
        MainModel.getInstance().showMetadataProperty().addListener((o, v, n) -> updateSplitPosition(n));
    }

    private void updateSplitPosition(Boolean showMetadata) {
        double newSize = showMetadata ? 0.5d : 1.0d;
        splitPane.setDividerPosition(0, newSize);
    }

    @Subscribe
    public void selectFolder(SelectFolderEvent e) {
        Window window = metadataPane.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File dir = directoryChooser.showDialog(window);
        if(dir == null || !dir.isDirectory())
            return;

        EventManager.getInstance().post(new FolderSelectedEvent(dir));
        MainModel.getInstance().setSelectedFolder(dir);
    }
}
