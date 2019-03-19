package controller;

import com.google.common.eventbus.Subscribe;
import controller.event.ExitEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.MainModel;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, Subscriber {

    @FXML private MainMenuController mainMenuController;
    @FXML private SideBarController sideBarController;
    @FXML private ContentAreaController contentAreaController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainModel.getInstance().register(this);
    }

    @Subscribe
    public void exit(ExitEvent actionEvent) {
        Platform.exit();
    }
}
