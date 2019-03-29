package controller;

import com.sun.tools.javac.Main;
import event.*;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import model.MainModel;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable, EventSubscriber {
    @FXML
    public ToggleButton editButton;

    // bind to disable button property
    public BooleanProperty emptySelectionProperty() {
        return MainModel.getInstance().emptySelectionProperty();
    }
    public boolean getEmptySelection()
    {
        return emptySelectionProperty().get();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editButton.selectedProperty().bindBidirectional(MainModel.getInstance().showEditPaneProperty());
    }

    public void exit(ActionEvent actionEvent) {
        EventManager.getInstance().post(new ExitEvent());
    }

    public void selectFolder(ActionEvent e) {
        EventManager.getInstance().post(new SelectFolderEvent());
    }

    public void saveChanges(ActionEvent e) {
        EventManager.getInstance().post(new SaveChangesEvent());
    }

    public void zoomReset(ActionEvent event) {
        EventManager.getInstance().post(new ZoomResetEvent());
    }

    public void zoomFit(ActionEvent event) {
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

    public void showAbout(ActionEvent e) {
        EventManager.getInstance().post(new ShowAboutEvent());
    }

    public void blackWhite(ActionEvent actionEvent) {
        EventManager.getInstance().post(new BlackWhiteEvent());
    }
}