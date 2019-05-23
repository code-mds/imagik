package ch.imagik.dialog;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import ch.imagik.model.MainModel;
import ch.imagik.model.ResizeInfo;
import javafx.scene.layout.VBox;

import java.util.Optional;

public final class ResizeDialog {
    private static void spinnerManipulator(Spinner<Integer> currentSpinner,SpinnerValueFactory<Integer> currentFactory){
        currentSpinner.setEditable(true);
        TextFormatter<Integer> spinnerTextFormatter = new TextFormatter<>(currentFactory.getConverter(), currentFactory.getValue());
        currentSpinner.getEditor().setTextFormatter(spinnerTextFormatter);
        currentFactory.valueProperty().bindBidirectional(spinnerTextFormatter.valueProperty());
    }

    public static Optional<ResizeInfo> show(int originalWidth, int originalHeight, boolean isMultiple) {
        double aspectRatio = (double) originalWidth / originalHeight;
        Dialog<ResizeInfo> dialog = new Dialog<>();
        String label;
        if(isMultiple) {
            label = MainModel.getInstance().getLocalizedString("resize_dialog.title_m");
        }else{
            label = MainModel.getInstance().getLocalizedString("resize_dialog.title_s");
        }
        dialog.setTitle(label);
        // Set the button types.
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridTop = new GridPane();
        gridTop.setHgap(12);
        gridTop.setVgap(6);
        gridTop.getColumnConstraints().add(0,new ColumnConstraints(130.0,60.0,60.0));
        gridTop.setPadding(new Insets(0,0,0,0));

        GridPane gridDown = new GridPane();
        gridDown.setHgap(12);
        gridDown.setVgap(6);
        gridDown.getColumnConstraints().add(0,new ColumnConstraints(130.0,60.0,60.0));
        gridDown.setPadding(new Insets(0,0,0,0));

        label = MainModel.getInstance().getLocalizedString("resize_dialog.percentage");

        RadioButton percentageSelector = new RadioButton(label);
        percentageSelector.setStyle("-fx-font-weight: bold");
        percentageSelector.getStyleClass().add("bold-label");
        percentageSelector.selectedProperty().setValue(true);
        if (isMultiple) {
            gridTop.add((new Label(label)), 0, 0);
        }
        SpinnerValueFactory<Integer> percentageFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200, 100);
        Spinner<Integer> percentageValue = new Spinner<>(percentageFactory);
        spinnerManipulator(percentageValue, percentageFactory);
        label = MainModel.getInstance().getLocalizedString("resize_dialog.pixel");

        RadioButton pixelSelector = new RadioButton(label);
        pixelSelector.setStyle("-fx-font-weight: bold");
        //pixelSelector.getStyleClass().add("bold-label");
        SpinnerValueFactory<Integer> widthFieldFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, originalWidth * 3, originalWidth);
        Spinner<Integer> widthField = new Spinner<>(widthFieldFactory);
        spinnerManipulator(widthField, widthFieldFactory);
        SpinnerValueFactory<Integer> heightFieldFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, originalHeight * 3, originalHeight);
        Spinner<Integer> heightField = new Spinner<>(heightFieldFactory);
        spinnerManipulator(heightField, heightFieldFactory);
        gridTop.add(new Label("1 - 200"), 0, 1);
        gridTop.add(percentageValue, 1, 1);

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
            gridTop.add(percentageSelector, 1, 0);
            gridDown.add(pixelSelector, 1, 0);
            label = MainModel.getInstance().getLocalizedString("resize_dialog.keepRatio");
            gridDown.add(new Label(label), 0, 1);
            gridDown.add(keepRatio, 1, 1);
            label = MainModel.getInstance().getLocalizedString("resize_dialog.width");
            gridDown.add(new Label(label), 0, 2);
            gridDown.add(widthField, 1, 2);
            label = MainModel.getInstance().getLocalizedString("resize_dialog.height");
            gridDown.add(new Label(label), 0, 3);
            gridDown.add(heightField, 1, 3);
        }
        setRightAlignment(gridTop);
        setRightAlignment(gridDown);
        VBox masterContainer = new VBox();
        masterContainer.setPadding(new Insets(18,18,18,18));
        masterContainer.setSpacing(18.0);
        masterContainer.getChildren().addAll(gridTop,gridDown);
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

        dialog.getDialogPane().setContent(masterContainer);


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
            return Optional.empty();
        }
    }

    private static void setRightAlignment(GridPane grid) {
        for (Node n : grid.getChildren()) {
            Integer col = GridPane.getColumnIndex(n);
            int colNumber = col == null ? 0 : col.intValue();
            if (colNumber == 0) {
                GridPane.setHalignment(n, HPos.RIGHT);
            }
        }
    }
}
