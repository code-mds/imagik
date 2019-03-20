import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application  {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Main.fxml"));
        primaryStage.setTitle("Imagik Image Viewer");
        primaryStage.getIcons().add(new Image(this.getClass().getResource("icon/wand.png").toString()));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
