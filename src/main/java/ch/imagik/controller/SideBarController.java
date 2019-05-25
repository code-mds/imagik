package ch.imagik.controller;

import ch.imagik.model.Folder;
import ch.imagik.service.ConfigService;
import com.google.common.eventbus.Subscribe;
import ch.imagik.event.EventManager;
import ch.imagik.event.FolderSelectedEvent;
import ch.imagik.event.SelectFolderEvent;
import ch.imagik.event.EventSubscriber;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import ch.imagik.model.MainModel;
import org.controlsfx.control.textfield.CustomTextField;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable, EventSubscriber {
    private final static double SPLIT_POS_METADATA_VISIBLE = 0.5;
    private final static double SPLIT_POS_METADATA_HIDDEN = 1.0;
    public FontIcon clearIcon;

    @FXML private AnchorPane metadataPane;
    @FXML private SplitPane splitPane;
    @FXML private CustomTextField searchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventManager.getInstance().register(this);
        searchField.textProperty().addListener((obs, old, newVal) -> MainModel.getInstance().setFilter(newVal));
        MainModel.getInstance().showMetadataProperty().addListener((o, v, n) -> updateSplitPosition(n));
        clearIcon.setOnMouseClicked( e -> searchField.textProperty().setValue(""));
    }

    private void updateSplitPosition(Boolean showMetadata) {
        double newSize = showMetadata ? SPLIT_POS_METADATA_VISIBLE : SPLIT_POS_METADATA_HIDDEN;
        splitPane.setDividerPosition(0, newSize);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void selectFolder(SelectFolderEvent e) {
        Window window = metadataPane.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File dir = directoryChooser.showDialog(window);
        if(dir == null || !dir.isDirectory())
            return;

        MainModel.getInstance().getConfigService().setConfigEntry(ConfigService.KEY_LAST_FOLDER, dir.getAbsolutePath());
        EventManager.getInstance().post(new FolderSelectedEvent(dir));
    }
}
