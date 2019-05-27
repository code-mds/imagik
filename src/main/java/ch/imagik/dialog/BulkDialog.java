package ch.imagik.dialog;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import ch.imagik.model.MainModel;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public final class BulkDialog {
    public static boolean showConfirmDialog(ObservableList<File> selectedFiles) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        String title = MainModel.getInstance().getLocalizedString("bulk_dialog.title");
        alert.setTitle(title);

        String text = String.format(MainModel.getInstance().getLocalizedString("bulk_dialog.header"), selectedFiles.size());
        alert.setHeaderText(text);

        String content = MainModel.getInstance().getLocalizedString("bulk_dialog.content");
        alert.setContentText(content);

        ListView<File> list = new ListView<>();
        list.setItems(selectedFiles);

        alert.getDialogPane().setExpandableContent(list);

        Image appIcon = MainModel.getInstance().getImageService().getAppIcon();
        Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(appIcon);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
