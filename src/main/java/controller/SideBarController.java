package controller;

import com.google.common.eventbus.Subscribe;
import event.EventManager;
import event.SelectFolderEvent;
import event.EventSubscriber;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import model.MainModel;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable, EventSubscriber {
    @FXML private Label folderName;
    @FXML private AnchorPane anchorPane;
    @FXML private TextField searchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventManager.getInstance().register(this);
        searchField.textProperty().addListener(this::search);
        //searchField.setPromptText("U+F002");
    }

    private void search(ObservableValue<? extends String> obs, String oldVal, String newVal) {
        MainModel.getInstance().setFilter(newVal);
    }

    public void selectFolder(ActionEvent e) {
        selectFolder(new SelectFolderEvent());
    }

    @Subscribe
    public void selectFolder(SelectFolderEvent e) {
        Window window = anchorPane.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File dir = directoryChooser.showDialog(window);
        if(dir == null || !dir.isDirectory())
            return;

        MainModel.getInstance().setSelectedFolder(dir);
        folderName.textProperty().setValue(dir.toString());
    }
}
