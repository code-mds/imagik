package ch.imagik.dialog;

import ch.imagik.model.MainModel;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AboutDialog {
    public static void show() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        String title = MainModel.getInstance().getLocalizedString("about_dialog.title");
        alert.setTitle(title);
        alert.setHeaderText(null);
        Image appIcon = MainModel.getInstance().getImageService().getAppIcon();
        ImageView img = new ImageView(appIcon);
        img.setFitWidth(70);
        img.setFitHeight(70);
        alert.setGraphic(img);

        String content = MainModel.getInstance().getLocalizedString("about_dialog.content");
        alert.setContentText(content);

        Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(appIcon);

        alert.showAndWait();
    }
}
