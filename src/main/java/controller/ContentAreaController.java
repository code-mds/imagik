package controller;

import com.google.common.eventbus.Subscribe;
import event.*;
import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.MainModel;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.awt.image.BufferedImage;

public class ContentAreaController implements Initializable, EventSubscriber {
    private static final float ZOOM_FACTOR = 0.1f;
    private BufferedImage currentImage;
    private File currentFile;
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
        try {
            currentFile = file;
            currentImage = ImageIO.read(currentFile);
            Image image = SwingFXUtils.toFXImage(currentImage, null);
            imageView.imageProperty().setValue(image);
        } catch (IOException e) {
            e.printStackTrace();
        }


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

    public void blackWhite(ActionEvent e) { blackWhite(new BlackWhiteEvent()); }

    @Subscribe
    public void blackWhite(BlackWhiteEvent e) {
        System.out.println("BlackAndWhite");
        ImagePlus imageToEdit = new ImagePlus("editing image",currentImage);
        ImageConverter imageToConvert = new ImageConverter(imageToEdit);
        imageToConvert.convertToGray32();
        currentImage=imageToEdit.getBufferedImage();
        imageView.setImage(SwingFXUtils.toFXImage(currentImage,null));

    }

    public void saveChanges(ActionEvent e) {
    }

    public void resetChanges(ActionEvent e) {
        resetChanges(new ResetChangesEvent());

    }
    @Subscribe
    private void resetChanges(ResetChangesEvent resetChangesEvent) {
        // TO DO private o public ?
        loadImage(currentFile);
    }
}
