package controller;

import com.google.common.eventbus.Subscribe;
import event.*;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import service.ImageService;
import model.MainModel;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.image.BufferedImage;

public class ContentAreaController implements Initializable, EventSubscriber {
    private static final double ZOOM_FACTOR = 0.1;
    private static final double NO_ZOOM = 1.0;

    private double currentZoom = NO_ZOOM;
    private BufferedImage currentImage;
    private List<File> selectedFiles;
    private ImageService imageService;

    @FXML ImageView imageView;
    @FXML ScrollPane scrollPane;

    // bind to disable button property
    public BooleanProperty disableEditProperty() {
        return MainModel.getInstance().disableEditProperty();
    }
    public boolean getDisableEdit()
    {
        return disableEditProperty().get();
    }

    public BooleanProperty disableZoomProperty() {
        return MainModel.getInstance().disableZoomProperty();
    }

    public BooleanProperty showEditPaneProperty() {
        return MainModel.getInstance().showEditPaneProperty();
    }
    public boolean getShowEditPane()
    {
        return showEditPaneProperty().get();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventManager.getInstance()
                .register(this);

        MainModel.getInstance()
                .getSelectedFiles()
                .addListener((ListChangeListener.Change<? extends File> l)-> loadImage( MainModel.getInstance().getSelectedFiles()));

        imageService = MainModel.getInstance().getImageService();
    }

    private void loadImage(List<File> selectedFiles) {
        this.selectedFiles=selectedFiles;

        if(selectedFiles.size() == 0){
            disableZoomProperty().setValue(true);
            disableEditProperty().setValue(true);
        }
        else if(selectedFiles.size()==1){
            try {
                disableZoomProperty().setValue(false);
                disableEditProperty().setValue(false);

                File currentFile = selectedFiles.get(0);
                currentImage = ImageIO.read(currentFile);
                Image image = SwingFXUtils.toFXImage(currentImage, null);
                zoomFit(new ZoomFitEvent());
                imageView.imageProperty().setValue(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            disableZoomProperty().setValue(true);
            disableEditProperty().setValue(false);
            // La selezione multipla non mostra nessuna immagine
            imageView.imageProperty().setValue(null);
        }

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
        if(selectedFiles.size() == 1){
            currentImage = ImageService.rotateLeft(currentImage);
            imageView.setImage(SwingFXUtils.toFXImage(currentImage,null));
        } else if(selectedFiles.size() > 1){
            if(BulkDialog.show(MainModel.getInstance().getSelectedFiles()))
                imageService.multiSelectionImageEditor(selectedFiles, ImageService::rotateLeft);
        }else{
            return; // TODO chiamare dialog per segnalare evetuale eccezione ?
        }

    }


    public void rotateRight(ActionEvent e) { rotateRight(new RotateRightEvent()); }

    @Subscribe
    public void rotateRight(RotateRightEvent e){
        if(selectedFiles.size() == 1){
            currentImage = ImageService.rotateRight(currentImage);
            imageView.setImage(SwingFXUtils.toFXImage(currentImage,null));
        } else if(selectedFiles.size() > 1 ){
            if(BulkDialog.show(MainModel.getInstance().getSelectedFiles()))
                imageService.multiSelectionImageEditor(selectedFiles, ImageService::rotateRight);
        }else{
            return; // TODO chiamare dialog per segnalare evetuale eccezione ?
        }
    }

    public void flipHorizontally(ActionEvent e) { flipHorizontally(new FlipHorizontallyEvent());
    }
    @Subscribe
    public void flipHorizontally(FlipHorizontallyEvent e){
        if(selectedFiles.size() == 1){
            currentImage = ImageService.flipHorizontally(currentImage);
            imageView.setImage(SwingFXUtils.toFXImage(currentImage,null));
        } else if(selectedFiles.size() > 1){
            if(BulkDialog.show(MainModel.getInstance().getSelectedFiles()))
                imageService.multiSelectionImageEditor(selectedFiles, ImageService::flipHorizontally);
        }else{
            return; // TODO chiamare dialog per segnalare evetuale eccezione ?
        }

    }

    public void flipVertically(ActionEvent e) { flipVertically(new FlipVerticallyEvent());
    }
    @Subscribe
    public void flipVertically(FlipVerticallyEvent e){
        if(selectedFiles.size() == 1){
            currentImage = ImageService.flipVertically(currentImage);
            imageView.setImage(SwingFXUtils.toFXImage(currentImage,null));
        } else if(selectedFiles.size() > 1){
            if(BulkDialog.show(MainModel.getInstance().getSelectedFiles()))
                imageService.multiSelectionImageEditor(selectedFiles, ImageService::flipVertically);
        }else{
            return; // TODO chiamare dialog per segnalare evetuale eccezione ?
        }

    }

    public void resize(ActionEvent e) { resize(new ResizeEvent()); }

    @Subscribe
    private void resize(ResizeEvent e) {
        System.out.println("RESIZE");
        ResizeDialog.show();
    }

    public void blackWhite(ActionEvent e) { blackWhite(new BlackWhiteEvent()); }

    @Subscribe
    public void blackWhite(BlackWhiteEvent e) {
        if(selectedFiles.size() == 1){
            currentImage = imageService.greyScale(currentImage);
            imageView.setImage(SwingFXUtils.toFXImage(currentImage,null));
        } else if(selectedFiles.size()>1){
            if(BulkDialog.show(MainModel.getInstance().getSelectedFiles()))
                imageService.multiSelectionImageEditor(selectedFiles, ImageService::greyScale);
        }else{
            return; // TODO chiamare dialog per segnalare evetuale eccezione ?
        }
    }

    public void saveChanges(ActionEvent e) {
    }

    public void resetChanges(ActionEvent e) {
        resetChanges(new ResetChangesEvent());

    }
    @Subscribe
    private void resetChanges(ResetChangesEvent e) {
        loadImage(selectedFiles);
    }

}
