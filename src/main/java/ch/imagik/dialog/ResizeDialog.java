package ch.imagik.dialog;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import ch.imagik.model.MainModel;
import ch.imagik.model.ResizeInfo;

import java.util.Optional;

public final class ResizeDialog {
    public static Optional<ResizeInfo> show(int originalWidth, int originalHeight, boolean isMultiple) {
        double aspectRatio = (double)originalWidth/originalHeight;
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
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        label = MainModel.getInstance().getLocalizedString("resize_dialog.percentage");
        RadioButton percentageSelector = new RadioButton(label);
        percentageSelector.selectedProperty().setValue(true);
        if(isMultiple){
            grid.add(new Label(label),0,0);
        }
        Spinner<Integer> percentageValue = new Spinner<>(1,200,100);
        label = MainModel.getInstance().getLocalizedString("resize_dialog.pixel");
        RadioButton pixelSelector = new RadioButton(label);
        Spinner<Integer> widthField= new Spinner<>(1, originalWidth*3, originalWidth);
        Spinner<Integer> heightField= new Spinner<>(1, originalHeight*3, originalHeight);
        grid.add(new Label("1 - 200"),0,1);
        grid.add(percentageValue,1,1);
        CheckBox keepRatio = new CheckBox();
        if(!isMultiple){
            pixelSelector.selectedProperty().setValue(false);
            ToggleGroup radioGroup = new ToggleGroup();
            pixelSelector.setToggleGroup(radioGroup);
            percentageSelector.setToggleGroup(radioGroup);
            keepRatio.selectedProperty().setValue(true);
            heightField.disableProperty().bind(percentageSelector.selectedProperty());
            widthField.disableProperty().bind(percentageSelector.selectedProperty());
            keepRatio.disableProperty().bind(percentageSelector.selectedProperty());
            percentageValue.disableProperty().bind(pixelSelector.selectedProperty());
            grid.add(percentageSelector,1,0);
            grid.add(pixelSelector,1,2);
            grid.add(new Label("Keep Ratio:"), 0, 3);
            grid.add(keepRatio, 1, 3);
            grid.add(new Label("Width:"), 0, 4);
            grid.add(widthField, 1, 4);
            grid.add(new Label("Height:"), 0, 5);
            grid.add(heightField, 1, 5);

        }
        heightField.valueProperty().addListener((obs, old, newVal) -> {
            if (keepRatio.isSelected()) {
                widthField.getValueFactory().setValue((int)Math.round(newVal * aspectRatio));
            }
        });

        widthField.valueProperty().addListener((obs, old, newVal) ->{
            if (keepRatio.isSelected()) {
                heightField.getValueFactory().setValue((int)Math.round(newVal/aspectRatio));
            }
        });

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                if(percentageSelector.isSelected()) {
                    return new ResizeInfo(percentageValue.getValue());
                }
                if(pixelSelector.isSelected()){
                    return new ResizeInfo(widthField.getValue(), heightField.getValue());
                }
            }

            return null;
        });

        return dialog.showAndWait();
    }
}
