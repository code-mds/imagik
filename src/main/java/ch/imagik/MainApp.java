package ch.imagik;

import ch.imagik.event.EventManager;
import ch.imagik.event.FolderSelectedEvent;
import ch.imagik.model.MainModel;
import ch.imagik.service.ConfigService;
import ch.imagik.service.ImageService;
import ch.imagik.service.ResourceService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.util.ResourceBundle;

public class MainApp extends Application {
    public static void main(String[] args) {
        MainApp.launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        ConfigService configService = MainModel.getInstance().getConfigService();
        ResourceService resourceService = MainModel.getInstance().getResourceService();
        ImageService imageService = MainModel.getInstance().getImageService();

        String language = configService.getConfigEntry(ConfigService.KEY_LANGUAGE);
        resourceService.setLanguage(language);
        Image appIcon = imageService.getAppIcon();

        initPrimaryStage(primaryStage, resourceService, appIcon);
        openLastFolder(configService);
    }

    private void initPrimaryStage(Stage primaryStage, ResourceService resourceService, Image appIcon) throws java.io.IOException {
        ResourceBundle bundle = resourceService.getBundle();
        Parent root = FXMLLoader.load(getClass().getResource("/ch/imagik/view/Main.fxml"), bundle);
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/ch/imagik/css/style.css");

        primaryStage.setTitle(resourceService.getLocalizedString("app_name"));
        primaryStage.getIcons().add(appIcon);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openLastFolder(ConfigService configService) {
        String lastFolder = configService.getConfigEntry(ConfigService.KEY_LAST_FOLDER);
        if(lastFolder != null) {
            File dir = new File(lastFolder);
            if(dir.exists() && dir.isDirectory())
                EventManager.getInstance().post(new FolderSelectedEvent(dir));
        }
    }
}
