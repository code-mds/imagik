package ch.imagik.service;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationService {
    public static void showWarning(String title, String text, Node owner, Image icon) {
        final int iconSize = 48;
        ImageView iconView = new ImageView(icon);
        iconView.setFitHeight(iconSize);
        iconView.setFitWidth(iconSize);

        Notifications.create()
                .title(title)
                .text(text)
                .graphic(iconView)
                .owner(owner)
                .position(Pos.BOTTOM_RIGHT)
                .hideAfter(Duration.seconds(4)).show();
    }

    public static void showInfo(String text, Node owner) {
        Notifications.create()
                .text(text)
                .owner(owner)
                .position(Pos.BOTTOM_RIGHT)
                .hideAfter(Duration.seconds(4)).showInformation();
    }
}
