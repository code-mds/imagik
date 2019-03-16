import controller.MainMenuController;
import controller.SideBarController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application  {

    @FXML private MainMenuController mainMenuController;
    @FXML private SideBarController sideBarController;
    @FXML private SideBarController contentAreaController;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));

        primaryStage.setTitle("Imagik Image Viewer");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
