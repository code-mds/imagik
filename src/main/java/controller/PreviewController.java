package controller;

import com.google.common.eventbus.Subscribe;
import controller.event.ZoomInEvent;
import controller.event.ZoomOutEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.MainModel;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class PreviewController  implements Initializable, Subscriber {
    private static final float ZOOM_FACTOR = 0.1f;

    @FXML
    ImageView imageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainModel.getInstance().register(this);
        MainModel.getInstance().addSelectedFileListener((observable, oldValue, newValue) -> loadImage(newValue));
    }

    private void loadImage(File file) {
        String uri = file.toURI().toString();
        imageView.imageProperty().setValue(new Image(uri));
    }

    public void zoomIn(ActionEvent actionEvent) {
        zoomIn(new ZoomInEvent());
    }

    public void zoomOut(ActionEvent actionEvent) {
        zoomOut(new ZoomOutEvent());
    }

    @Subscribe
    public void zoomIn(ZoomInEvent event) {
        System.out.println("ZOOM IN ACTION");
//        imageView.setFitHeight(imageView.getFitHeight() / ZOOM_FACTOR);
//        imageView.setFitWidth(imageView.getFitWidth() * ZOOM_FACTOR);
    }

    @Subscribe
    public void zoomOut(ZoomOutEvent event) {
        System.out.println("ZOOM OUT ACTION");
//        imageView.setFitHeight(imageView.getFitHeight() * ZOOM_FACTOR);
//        imageView.setFitWidth(imageView.getFitWidth() * ZOOM_FACTOR);
    }
}
