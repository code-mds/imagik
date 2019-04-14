package ch.imagik.controller;

import ch.imagik.model.Folder;
import com.google.common.eventbus.Subscribe;
import ch.imagik.event.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import ch.imagik.service.ImageService;
import ch.imagik.model.MainModel;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, EventSubscriber {
    @FXML private Label currentFolder;
    @FXML private Label totalFiles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventManager.getInstance().register(this);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void exit(ExitEvent e) {
        Platform.exit();
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void showAbout(ShowAboutEvent e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        String title = MainModel.getInstance().getLocalizedString("about_dialog.title");
        alert.setTitle(title);
        alert.setHeaderText(null);
        ImageView img = new ImageView(this.getClass().getResource("/ch/imagik/icon/wand.png").toString());
        img.setFitWidth(70);
        img.setFitHeight(70);
        alert.setGraphic(img);

        String content = MainModel.getInstance().getLocalizedString("about_dialog.content");
        alert.setContentText(content);

        alert.showAndWait();
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void folderSelected(FolderSelectedEvent e) {
        Folder folder = e.getFolder();
        updateStatusBar(folder);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void folderRefresh(FolderRefreshEvent e) {
        Folder folder = e.getFolder();
        updateStatusBar(folder);
    }

    private void updateStatusBar(Folder folder) {
        currentFolder.setText(folder.toString());
        File[] files = ImageService.listImages(folder);
        totalFiles.setText(String.format("%d files", files.length));
    }
}
