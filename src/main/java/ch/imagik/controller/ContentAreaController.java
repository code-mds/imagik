package ch.imagik.controller;

import ch.imagik.dialog.BulkDialog;
import ch.imagik.dialog.ResizeDialog;
import ch.imagik.model.Folder;
import com.google.common.eventbus.Subscribe;
import ch.imagik.event.*;
import ij.gui.SaveChangesDialog;
import ij.io.SaveDialog;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ch.imagik.service.ImageService;
import ch.imagik.model.MainModel;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.commons.io.FilenameUtils;


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

    @FXML private ImageView imageView;
    @FXML private ScrollPane scrollPane;
    @FXML private VBox editPane;

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
        EventManager.getInstance().register(this);

        MainModel.getInstance()
                .getSelectedFiles()
                .addListener((ListChangeListener.Change<? extends File> l)-> loadImage( MainModel.getInstance().getSelectedFiles()));

        imageService = MainModel.getInstance().getImageService();
        setBackgroundNoFolder();
    }

    private void setBackgroundOnCondition(String url) {
        try {
            currentImage=ImageIO.read(this.getClass().getResource(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImage(SwingFXUtils.toFXImage(currentImage, null));
        zoomFit(new ZoomFitEvent());
    }

    private void setBackgroundNoFolder() {
        setBackgroundOnCondition("/ch/imagik/background/home_1.jpg");
    }

    private void setBackgroundNoSelection() {
        setBackgroundOnCondition("/ch/imagik/background/home_2.jpg");
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void folderSelected(FolderSelectedEvent e) {
        setBackgroundNoSelection();
    }

    private void loadImage(List<File> selectedFiles) {
        this.selectedFiles=selectedFiles;

        if(selectedFiles.size() == 0){
            disableZoomProperty().setValue(true);
            disableEditProperty().setValue(true);
            imageView.imageProperty().setValue(null);
            setBackgroundNoSelection();
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


    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void zoomFit(ZoomFitEvent e) {
        currentZoom = Math.min(scrollPane.getWidth() / currentImage.getWidth(), NO_ZOOM);

        imageView.fitWidthProperty().bind(scrollPane.widthProperty());
        imageView.fitHeightProperty().bind(scrollPane.heightProperty());
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void zoomReset(ZoomResetEvent e) {
        imageView.fitWidthProperty().unbind();
        imageView.fitHeightProperty().unbind();

        currentZoom = NO_ZOOM;
        updateImageView();
    }

    private void updateImageView() {
        imageView.setFitHeight(currentImage.getHeight() * currentZoom);
        imageView.setFitWidth(currentImage.getWidth() * currentZoom);
    }


    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void zoomIn(ZoomInEvent e) {
        imageView.fitWidthProperty().unbind();
        imageView.fitHeightProperty().unbind();

        currentZoom = Math.min(currentZoom + ZOOM_FACTOR, NO_ZOOM);
        updateImageView();
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void zoomOut(ZoomOutEvent e) {
        imageView.fitWidthProperty().unbind();
        imageView.fitHeightProperty().unbind();

        currentZoom = Math.max(currentZoom - ZOOM_FACTOR, ZOOM_FACTOR);
        updateImageView();
    }

    @FXML private void rotateLeft(ActionEvent e) { rotateLeft(new RotateLeftEvent());
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void rotateLeft(RotateLeftEvent e){
        if(selectedFiles.size() == 1){
            currentImage = ImageService.rotateLeft(currentImage);
            imageView.setImage(SwingFXUtils.toFXImage(currentImage,null));
        } else if(selectedFiles.size() > 1){
            if(BulkDialog.show(MainModel.getInstance().getSelectedFiles()))
                imageService.multiSelectionImageEdit(selectedFiles, ImageService::rotateLeft);
        }
    }


    @FXML private void rotateRight(ActionEvent e) { rotateRight(new RotateRightEvent()); }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void rotateRight(RotateRightEvent e){
        if(selectedFiles.size() == 1){
            currentImage = ImageService.rotateRight(currentImage);
            imageView.setImage(SwingFXUtils.toFXImage(currentImage,null));
        } else if(selectedFiles.size() > 1 ){
            if(BulkDialog.show(MainModel.getInstance().getSelectedFiles()))
                imageService.multiSelectionImageEdit(selectedFiles, ImageService::rotateRight);
        }
    }

    @FXML private void flipHorizontally(ActionEvent e) { flipHorizontally(new FlipHorizontallyEvent());
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void flipHorizontally(FlipHorizontallyEvent e){
        if(selectedFiles.size() == 1){
            currentImage = ImageService.flipHorizontally(currentImage);
            imageView.setImage(SwingFXUtils.toFXImage(currentImage,null));
        } else if(selectedFiles.size() > 1){
            if(BulkDialog.show(MainModel.getInstance().getSelectedFiles()))
                imageService.multiSelectionImageEdit(selectedFiles, ImageService::flipHorizontally);
        }
    }

    @FXML private void flipVertically(ActionEvent e) { flipVertically(new FlipVerticallyEvent());
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void flipVertically(FlipVerticallyEvent e){
        if(selectedFiles.size() == 1){
            currentImage = ImageService.flipVertically(currentImage);
            imageView.setImage(SwingFXUtils.toFXImage(currentImage,null));
        } else if(selectedFiles.size() > 1){
            if(BulkDialog.show(MainModel.getInstance().getSelectedFiles()))
                imageService.multiSelectionImageEdit(selectedFiles, ImageService::flipVertically);
        }
    }

    @FXML private void resize(ActionEvent e) { resize(new ResizeEvent()); }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void resize(ResizeEvent e) {
        System.out.println("RESIZE");
        if(selectedFiles.size()>1){
            ResizeDialog.show(1,1,true);
        }else if(selectedFiles.size()==1){
            ResizeDialog.show(currentImage.getWidth(),currentImage.getHeight(),false);
        }

    }

    @FXML private void blackWhite(ActionEvent e) { blackWhite(new BlackWhiteEvent()); }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void blackWhite(BlackWhiteEvent e) {
        if(selectedFiles.size() == 1){
            currentImage = ImageService.greyScale(currentImage);
            imageView.setImage(SwingFXUtils.toFXImage(currentImage,null));
        } else if(selectedFiles.size()>1){
            if(BulkDialog.show(MainModel.getInstance().getSelectedFiles()))
                imageService.multiSelectionImageEdit(selectedFiles, ImageService::greyScale);
        }
    }

    @FXML private void saveChanges(ActionEvent e) {
        EventManager.getInstance().post(new SaveChangesEvent());
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void saveChanges(SaveChangesEvent e) {
        saveImage(selectedFiles);
    }

    private void saveImage(List<File> selectedFiles) {
        if(selectedFiles.size() == 1){
            Window window = editPane.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images",
                    "*.jpg", "*.jpeg", "*.png", "*.gif");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(window);
            if(file == null)
                return;

            try {
                ImageIO.write(currentImage, FilenameUtils.getExtension(file.getName()), file);

                // SAVE AS IN SAME DIRECTORY, NEED TO REFRESH THUMB CONTENT
                if(!file.equals(selectedFiles.get(0)) && file.getParent().equals(selectedFiles.get(0).getParent())) {
                    EventManager.getInstance().post(new FolderRefreshEvent(new Folder(file.getParentFile())));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML private void resetChanges(ActionEvent e) {
        resetChanges(new ResetChangesEvent());
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void resetChanges(ResetChangesEvent e) {
        loadImage(selectedFiles);
    }

}
