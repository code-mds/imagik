package ch.imagik.controller;

import ch.imagik.event.*;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ToggleButton;
import ch.imagik.model.MainModel;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable, EventSubscriber {
    @FXML private ToggleButton editButton;
    @FXML private CheckMenuItem showEditor;
    @FXML private CheckMenuItem showMetadata;

    // bind to disable button property
    public BooleanProperty showEditPaneProperty()  {
        return MainModel.getInstance().showEditPaneProperty();
    }
    public boolean getShowEditPane()
    {
        return showEditPaneProperty().get();
    }

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

    public void selectFolder(ActionEvent e) {
        EventManager.getInstance().post(new SelectFolderEvent());
    }

    public void saveChanges(ActionEvent e) {
        EventManager.getInstance().post(new SaveChangesEvent());
    }

    public void zoomReset(ActionEvent e) {
        EventManager.getInstance().post(new ZoomResetEvent());
    }

    public void zoomFit(ActionEvent e) {
        EventManager.getInstance().post(new ZoomFitEvent());
    }

    public void zoomIn(ActionEvent e) { EventManager.getInstance().post(new ZoomInEvent()); }

    public void zoomOut(ActionEvent e)  {
        EventManager.getInstance().post(new ZoomOutEvent());
    }

    public void rotateLeft(ActionEvent e) {
        EventManager.getInstance().post(new RotateLeftEvent());
    }

    public void rotateRight(ActionEvent e) {
        EventManager.getInstance().post(new RotateRightEvent());
    }

    public void flipHorizontally(ActionEvent e) {
        EventManager.getInstance().post(new FlipHorizontallyEvent());
    }

    public void flipVertically(ActionEvent e) {
        EventManager.getInstance().post(new FlipVerticallyEvent());
    }

    public void showAbout(ActionEvent e) {
        EventManager.getInstance().post(new ShowAboutEvent());
    }

    public void blackWhite(ActionEvent e) {
        EventManager.getInstance().post(new BlackWhiteEvent());
    }

    public void resize(ActionEvent e) { EventManager.getInstance().post(new ResizeEvent()); }
}