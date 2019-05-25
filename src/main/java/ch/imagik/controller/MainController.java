package ch.imagik.controller;

import ch.imagik.dialog.AboutDialog;
import ch.imagik.model.Folder;
import com.google.common.eventbus.Subscribe;
import ch.imagik.event.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import ch.imagik.service.ImageService;
import ch.imagik.model.MainModel;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, EventSubscriber {
    @FXML private SplitPane splitPane;
    @FXML private Label currentFolder;
    @FXML private Label totalFiles;
    @FXML private Node welcomePane1;

    private int totalNr;

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
        AboutDialog.show();
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void folderSelected(FolderSelectedEvent e) {
        welcomePane1.visibleProperty().setValue(false);
        splitPane.visibleProperty().setValue(true);

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
        totalNr = files.length;
        totalFiles.setText(String.format("%d files", totalNr));
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void brokenImageHandler(BrokenImageEvent e) {
        totalNr--;
        totalFiles.setText(String.format("%d files", totalNr));
    }
}
