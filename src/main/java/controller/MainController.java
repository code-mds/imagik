package controller;

import com.google.common.eventbus.Subscribe;
import event.*;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.MainModel;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable, EventSubscriber {
    public Label currentFolder;
    public Label totalFiles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventManager.getInstance().register(this);
    }

    @Subscribe
    public void exit(ExitEvent e) {
        Platform.exit();
    }

    @Subscribe
    public void showAbout(ShowAboutEvent e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        String title = MainModel.getInstance().getLocalizedString("about_dialog.title");
        alert.setTitle(title);
        alert.setHeaderText(null);
        ImageView img = new ImageView(this.getClass().getResource("/icon/wand.png").toString());
        img.setFitWidth(70);
        img.setFitHeight(70);
        alert.setGraphic(img);

        String content = MainModel.getInstance().getLocalizedString("about_dialog.content");
        alert.setContentText(content);

        alert.showAndWait();
    }

    @Subscribe
    public void folderSelected(FolderSelectedEvent e) {
        File folder = e.getFolder();

        currentFolder.setText(e.getFolder().toString());
        totalFiles.setText(String.format("%d files", Objects.requireNonNull(folder.list()).length));
    }
}
