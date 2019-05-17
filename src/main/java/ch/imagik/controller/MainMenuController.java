package ch.imagik.controller;

import ch.imagik.event.*;
import ch.imagik.model.Folder;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ToggleButton;
import ch.imagik.model.MainModel;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable, EventSubscriber {
    @FXML private ToggleButton editButton;
    @FXML private CheckMenuItem showEditor;
    @FXML private CheckMenuItem showMetadata;

    public BooleanProperty showMetadataProperty()  {
        return MainModel.getInstance().showMetadataProperty();
    }
    public boolean getShowMetadata()
    {
        return showMetadataProperty().get();
    }

    public BooleanProperty disableEditProperty() {
        return MainModel.getInstance().disableEditProperty();
    }
    public boolean getDisableEdit() {
        return disableEditProperty().get();
    }

    public BooleanProperty disableZoomProperty() {
        return MainModel.getInstance().disableZoomProperty();
    }
    public boolean getDisableZoom()
    {
        return disableZoomProperty().get();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editButton.selectedProperty().bindBidirectional(MainModel.getInstance().showEditPaneProperty());
        showEditor.selectedProperty().bindBidirectional(MainModel.getInstance().showEditPaneProperty());
        showMetadata.selectedProperty().bindBidirectional(MainModel.getInstance().showMetadataProperty());
    }

    @FXML private void selectFolder(ActionEvent e) {
        EventManager.getInstance().post(new SelectFolderEvent());
    }

    @FXML private void saveChanges(ActionEvent e) {
        EventManager.getInstance().post(new SaveChangesEvent());
    }

    @FXML private void zoomReset(ActionEvent e) {
        EventManager.getInstance().post(new ZoomResetEvent());
    }

    @FXML private void zoomFit(ActionEvent e) {
        EventManager.getInstance().post(new ZoomFitEvent());
    }

    @FXML private void zoomIn(ActionEvent e) { EventManager.getInstance().post(new ZoomInEvent()); }

    @FXML private void zoomOut(ActionEvent e)  {
        EventManager.getInstance().post(new ZoomOutEvent());
    }

    @FXML private void rotateLeft(ActionEvent e) {
        EventManager.getInstance().post(new RotateLeftEvent());
    }

    @FXML private void rotateRight(ActionEvent e) {
        EventManager.getInstance().post(new RotateRightEvent());
    }

    @FXML private void flipHorizontally(ActionEvent e) {
        EventManager.getInstance().post(new FlipHorizontallyEvent());
    }

    @FXML private void flipVertically(ActionEvent e) {
        EventManager.getInstance().post(new FlipVerticallyEvent());
    }

    @FXML private void showAbout(ActionEvent e) {
        EventManager.getInstance().post(new ShowAboutEvent());
    }

    @FXML private void blackWhite(ActionEvent e) {
        EventManager.getInstance().post(new BlackWhiteEvent());
    }

    @FXML private void resize(ActionEvent e) { EventManager.getInstance().post(new ResizeEvent()); }
}