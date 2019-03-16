import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {

    @FXML private MetadataController metadataController;
    @FXML private MainMenuController mainMenuController;
    @FXML private PreviewController previewController;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

        primaryStage.setTitle("Imagik Image Viewer");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InputStream input = getClass().getResourceAsStream("test.jpg");
        metadataController.loadMetadata(input);
    }
}
