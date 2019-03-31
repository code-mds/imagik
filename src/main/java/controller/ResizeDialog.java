package controller;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.MainModel;
import model.ResizeInfo;

import java.util.Optional;

public final class ResizeDialog {
    public static Optional<ResizeInfo> show() {
        Dialog<ResizeInfo> dialog = new Dialog<>();
        String title = MainModel.getInstance().getLocalizedString("resize_dialog.title");
        dialog.setTitle(title);

        String text = MainModel.getInstance().getLocalizedString("resize_dialog.header");
        dialog.setHeaderText(text);

        // Set the button types.
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Spinner<Integer> widthField = new Spinner<>(1, 10, 2);
        Spinner<Integer> heightField = new Spinner<>(1, 10, 2);

        grid.add(new Label("Width:"), 0, 0);
        grid.add(widthField, 1, 0);
        grid.add(new Label("Height:"), 0, 1);
        grid.add(heightField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new ResizeInfo(widthField.getValue(), heightField.getValue());
            }
            return null;
        });

        return dialog.showAndWait();
    }
}
