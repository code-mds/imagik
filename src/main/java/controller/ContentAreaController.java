package controller;

import com.google.common.eventbus.Subscribe;
import event.*;
import ij.ImagePlus;
import ij.process.ImageConverter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ImageService;
import model.MainModel;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.awt.image.BufferedImage;

public class ContentAreaController implements Initializable, EventSubscriber {
    private static final double ZOOM_FACTOR = 0.1;
    private static final double NO_ZOOM = 1.0;

    private double currentZoom = NO_ZOOM;
    private BufferedImage currentImage;
    private File currentFile;
    private ImageService imageService;

    @FXML ImageView imageView;
    @FXML ScrollPane scrollPane;

    // bind to disable button property
    private BooleanProperty emptySelection = new SimpleBooleanProperty(true);
    public BooleanProperty emptySelectionProperty() {
        return emptySelection;
    }
    public boolean getEmptySelection()
    {
        return emptySelection.get();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventManager.getInstance()
                .register(this);
        MainModel.getInstance()
                .addSelectedFileListener((observable, oldValue, newValue) -> loadImage(newValue));

        imageService = MainModel.getInstance().getImageService();
    }

    private void loadImage(File file) {
        try {
            Image image = null;
            currentFile = file;
            emptySelection.set(currentFile == null);
            if(file != null) {
                currentImage = ImageIO.read(currentFile);
                image = SwingFXUtils.toFXImage(currentImage, null);
                zoomFit(new ZoomFitEvent());
            }
            imageView.imageProperty().setValue(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void zoomIn(ActionEvent e) {
        System.out.println("ACTION EVENT ZOOM IN");
        zoomIn(new ZoomInEvent());
    }

    public void zoomOut(ActionEvent e) {
        zoomOut(new ZoomOutEvent());
    }

    public void zoomReset(ActionEvent event) {
        zoomReset(new ZoomResetEvent());
    }
    public void zoomFit(ActionEvent event) {
        zoomFit(new ZoomFitEvent());
    }

    @Subscribe
    public void zoomFit(ZoomFitEvent e) {
        currentZoom = Math.min(scrollPane.getWidth() / currentImage.getWidth(), NO_ZOOM);

        imageView.fitWidthProperty().bind(scrollPane.widthProperty());
        imageView.fitHeightProperty().bind(scrollPane.heightProperty());
    }

    @Subscribe
    public void zoomReset(ZoomResetEvent e) {
        imageView.fitWidthProperty().unbind();
        imageView.fitHeightProperty().unbind();

        currentZoom = NO_ZOOM;
        updateImageView();
    }

    private void updateImageView() {
        imageView.setFitHeight(currentImage.getHeight() * currentZoom);
        imageView.setFitWidth(currentImage.getWidth() * currentZoom);
    }


    @Subscribe
    public void zoomIn(ZoomInEvent e) {
        imageView.fitWidthProperty().unbind();
        imageView.fitHeightProperty().unbind();

        currentZoom = Math.min(currentZoom + ZOOM_FACTOR, NO_ZOOM);
        updateImageView();
    }

    @Subscribe
    public void zoomOut(ZoomOutEvent e) {
        imageView.fitWidthProperty().unbind();
        imageView.fitHeightProperty().unbind();

        currentZoom = Math.max(currentZoom - ZOOM_FACTOR, ZOOM_FACTOR);
        updateImageView();
    }

    public void rotateLeft(ActionEvent e) { rotateLeft(new RotateLeftEvent());
    }
    @Subscribe
    public void rotateLeft(RotateLeftEvent e){
        currentImage = imageService.rotateLeft(currentImage);
        imageView.setImage(SwingFXUtils.toFXImage(currentImage,null));
    }
    public void rotateRight(ActionEvent e) { rotateRight(new RotateRightEvent());
    }
    @Subscribe
    public void rotateRight(RotateRightEvent e){
        System.out.println("ROTATE RIGHT");
        currentImage = imageService.rotateRight(currentImage);
        imageView.setImage(SwingFXUtils.toFXImage(currentImage,null));
    }
    public void resize(ActionEvent e) {
    }

    public void blackWhite(ActionEvent e) { blackWhite(new BlackWhiteEvent()); }

    @Subscribe
    public void blackWhite(BlackWhiteEvent e) {
        currentImage = imageService.greyScale(currentImage);
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
