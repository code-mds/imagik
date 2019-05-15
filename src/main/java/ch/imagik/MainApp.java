package ch.imagik;

import ch.imagik.event.EventManager;
import ch.imagik.event.FolderSelectedEvent;
import ch.imagik.model.Folder;
import ch.imagik.model.MainModel;
import ch.imagik.service.ConfigService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainApp extends Application {
    public static void main(String[] args) {
        MainApp.launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Locale locale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("ch.imagik.bundles.imagik", locale);
        Parent root = FXMLLoader.load(getClass().getResource("/ch/imagik/view/Main.fxml"), bundle);
        primaryStage.setTitle("Imagik Image Viewer");
        primaryStage.getIcons().add(new Image(this.getClass().getResource("/ch/imagik/icon/wand.png").toString()));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/ch/imagik/css/style.css");

        primaryStage.setScene(scene);
        primaryStage.show();

        String lastFolder = MainModel.getInstance().getConfigService().getEntry(ConfigService.KEY_LAST_FOLDER);
        if(lastFolder != null) {
            File dir = new File(lastFolder);
            EventManager.getInstance().post(new FolderSelectedEvent(new Folder(dir)));
        }
    }
}
