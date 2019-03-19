package controller;

import event.*;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable, EventSubscriber {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void exit(ActionEvent actionEvent) {
        EventManager.getInstance().post(new ExitEvent());
    }

    public void openFolder(ActionEvent e) {
        EventManager.getInstance().post(new SelectFolderEvent());
    }

    public void saveChanges(ActionEvent e) {
        EventManager.getInstance().post(new SaveChangesEvent());
    }

    public void zoomIn(ActionEvent e) {
        EventManager.getInstance().post(new ZoomInEvent());
    }

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
}