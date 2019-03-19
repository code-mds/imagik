package controller;

import com.google.common.eventbus.Subscribe;
import event.EventManager;
import event.ExitEvent;
import event.EventSubscriber;
import javafx.application.Platform;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, EventSubscriber {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventManager.getInstance().register(this);
    }

    @Subscribe
    public void exit(ExitEvent actionEvent) {
        Platform.exit();
    }
}
