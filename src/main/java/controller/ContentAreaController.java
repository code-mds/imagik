package controller;

import com.google.common.eventbus.Subscribe;
import event.EventManager;
import event.EventSubscriber;
import event.ZoomInEvent;
import event.ZoomOutEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.MainModel;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ContentAreaController implements Initializable, EventSubscriber {
    private static final float ZOOM_FACTOR = 0.1f;

    @FXML ImageView imageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventManager.getInstance()
                .register(this);
        MainModel.getInstance()
                .addSelectedFileListener((observable, oldValue, newValue) -> loadImage(newValue));

        //imageView.fitWidthProperty().bind(imageView.getScene())
    }

    private void loadImage(File file) {
        String uri = file.toURI().toString();
        imageView.imageProperty().setValue(new Image(uri));
    }

    public void zoomIn(ActionEvent e) {
        zoomIn(new ZoomInEvent());
    }

    public void zoomOut(ActionEvent e) {
        zoomOut(new ZoomOutEvent());
    }

    @Subscribe
    public void zoomIn(ZoomInEvent e) {
        System.out.println("ZOOM IN ACTION");
//        imageView.setFitHeight(imageView.getFitHeight() / ZOOM_FACTOR);
//        imageView.setFitWidth(imageView.getFitWidth() * ZOOM_FACTOR);
    }

    @Subscribe
    public void zoomOut(ZoomOutEvent e) {
        System.out.println("ZOOM OUT ACTION");
//        imageView.setFitHeight(imageView.getFitHeight() * ZOOM_FACTOR);
//        imageView.setFitWidth(imageView.getFitWidth() * ZOOM_FACTOR);
    }

    public void rotateLeft(ActionEvent e) {
    }

    public void rotateRight(ActionEvent e) {
    }

    public void resize(ActionEvent e) {
    }

    public void blackWhite(ActionEvent e) {
    }

    public void saveChanges(ActionEvent e) {
    }

    public void resetChanges(ActionEvent e) {
    }
}
