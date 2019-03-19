package controller;

import controller.event.ExitEvent;
import controller.event.SelectFolderEvent;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import model.MainModel;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable, Subscriber {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void exit(ActionEvent actionEvent) {
        MainModel.getInstance().post(new ExitEvent());
    }

    public void open(ActionEvent actionEvent) {
        MainModel.getInstance().post(new SelectFolderEvent());
    }
}