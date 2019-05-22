package ch.imagik.dialog;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import ch.imagik.model.MainModel;
import ch.imagik.model.ResizeInfo;

import java.sql.SQLOutput;
import java.util.Optional;

public final class ResizeDialog {
    private static void spinnerManipulator(Spinner<Integer> currentSpinner,SpinnerValueFactory<Integer> currentFactory){
        currentSpinner.setEditable(true);
        TextFormatter<Integer> spinnerTextFormatter = new TextFormatter<Integer>(currentFactory.getConverter(), currentFactory.getValue());
        currentSpinner.getEditor().setTextFormatter(spinnerTextFormatter);
        currentFactory.valueProperty().bindBidirectional(spinnerTextFormatter.valueProperty());
    }
    public static Optional<ResizeInfo> show(int originalWidth, int originalHeight, boolean isMultiple) {
        double aspectRatio = (double) originalWidth / originalHeight;
        //System.out.println("Aspect Ratio dell'immagne: "+aspectRatio);
        Dialog<ResizeInfo> dialog = new Dialog<>();
        String label;
        label = MainModel.getInstance().getLocalizedString("resize_dialog.title");
        dialog.setTitle(label);

        label = MainModel.getInstance().getLocalizedString("resize_dialog.header");
        dialog.setHeaderText(label);

        // Set the button types.
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(18);
        grid.setVgap(12);
        System.out.println("Numero colonne: " + grid.getColumnCount());
        label = MainModel.getInstance().getLocalizedString("resize_dialog.percentage");
        RadioButton percentageSelector = new RadioButton(label);
        percentageSelector.selectedProperty().setValue(true);
        if (isMultiple) {
            grid.add((new Label(label)), 0, 0);
        }
        SpinnerValueFactory<Integer> percentageFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200, 100);
        Spinner<Integer> percentageValue = new Spinner<Integer>(percentageFactory);
        spinnerManipulator(percentageValue, percentageFactory);
        label = MainModel.getInstance().getLocalizedString("resize_dialog.pixel");
        RadioButton pixelSelector = new RadioButton(label);
        SpinnerValueFactory<Integer> widthFieldFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, originalWidth * 3, originalWidth);
        Spinner<Integer> widthField = new Spinner<>(widthFieldFactory);
        spinnerManipulator(widthField, widthFieldFactory);
        SpinnerValueFactory<Integer> heightFieldFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, originalHeight * 3, originalHeight);
        Spinner<Integer> heightField = new Spinner<>(heightFieldFactory);
        spinnerManipulator(heightField, heightFieldFactory);
        grid.add(new Label("1 - 200"), 0, 1);
        grid.add(percentageValue, 1, 1);
        CheckBox keepRatio = new CheckBox();
        if (!isMultiple) {
            pixelSelector.selectedProperty().setValue(false);
            ToggleGroup radioGroup = new ToggleGroup();
            pixelSelector.setToggleGroup(radioGroup);
            percentageSelector.setToggleGroup(radioGroup);
            keepRatio.selectedProperty().setValue(true);
            heightField.disableProperty().bind(percentageSelector.selectedProperty());
            widthField.disableProperty().bind(percentageSelector.selectedProperty());
            keepRatio.disableProperty().bind(percentageSelector.selectedProperty());
            percentageValue.disableProperty().bind(pixelSelector.selectedProperty());
            grid.add(percentageSelector, 1, 0);
            grid.add(pixelSelector, 1, 2);
            label = MainModel.getInstance().getLocalizedString("resize_dialog.keepRatio");
            grid.add(new Label(label), 0, 3);
            grid.add(keepRatio, 1, 3);
            label = MainModel.getInstance().getLocalizedString("resize_dialog.width");
            grid.add(new Label(label), 0, 4);
            grid.add(widthField, 1, 4);
            label = MainModel.getInstance().getLocalizedString("resize_dialog.height");
            grid.add(new Label(label), 0, 5);
            grid.add(heightField, 1, 5);
        }
        for (Node n : grid.getChildren()) {
            Integer col = GridPane.getColumnIndex(n);
            int colNumber = col == null ? 0 : col.intValue();
            if (colNumber == 0) {
                GridPane.setHalignment(n, HPos.RIGHT);
            }
        }

        heightField.valueProperty().addListener((obs, old, newVal) -> {
            if (keepRatio.isSelected()) {
                widthField.getValueFactory().setValue((int) Math.round(newVal * aspectRatio));
            }
        });

        widthField.valueProperty().addListener((obs, old, newVal) -> {
            if (keepRatio.isSelected()) {
                heightField.getValueFactory().setValue((int) Math.round(newVal / aspectRatio));
            }
        });

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                if (percentageSelector.isSelected()) {
                    return new ResizeInfo(percentageValue.getValue());
                }
                if (pixelSelector.isSelected()) {
                    return new ResizeInfo(widthField.getValue(), heightField.getValue());
                }
            }

            return null;
        });
        try {
            return dialog.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
