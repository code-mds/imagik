package controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import model.ImageService;
import model.MainModel;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class ThumbnailController implements Initializable {
    private final String[] EXTENSIONS = new String[] {"jpg", "jpeg", "png", "gif"};
    private final int THUMB_SIZE = 50;
    @FXML private ListView<File> thumbListView;
    private final ObservableList<File> imageList = FXCollections.observableArrayList();
    private final FilteredList<File> filteredImageList = new FilteredList<>(imageList);

    private ImageService imageService;
    private MainModel mainModel;

    private DateFormat dateFormatter = DateFormat.getDateTimeInstance(
            DateFormat.SHORT,
            DateFormat.SHORT,
            Locale.getDefault());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainModel = MainModel.getInstance();
        mainModel.addSelectedFolderListener((observable, oldValue, newValue) -> listFiles(newValue));
        mainModel.addFilterListener((observable, oldValue, newValue) -> filterList(newValue));
        imageService = mainModel.getImageService();
        thumbListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        thumbListView.setItems(filteredImageList);
        thumbListView.getSelectionModel()
                .getSelectedItems()
                .addListener((ListChangeListener.Change<? extends File> l) -> MainModel.getInstance().setSelectedFiles(l.getList()));

        /*thumbListView.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> MainModel.getInstance().setSelectedFile(newValue));
        */
        thumbListView.setCellFactory(param -> new ListCell<>() {
            private ImageView imageView = new ImageView();

            @Override
            public void updateItem(File file, boolean empty) {
                super.updateItem(file, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    imageView.fitHeightProperty().setValue(THUMB_SIZE);
                    imageView.fitWidthProperty().setValue(THUMB_SIZE);
                    imageView.setImage(imageService.getThumbnail(file, THUMB_SIZE));

                    String dateModified = dateFormatter.format(new Date(file.lastModified()));
                    String text = String.format("%s\n%s\n%s",
                            file.getName(),
                            dateModified,
                            humanReadableByteCount(file.length(), true));

                    setText(text);
                    setGraphic(imageView);
                }
            }
        });
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    private void filterList(String filter) {
        filteredImageList.setPredicate(file -> file.getName().toLowerCase().contains(filter));
    }

    private boolean acceptExtension(String name) {
        for (String ext : EXTENSIONS) {
            if(name.endsWith(ext))
                return true;
        }
        return false;
    }

    private void listFiles(File dir) {
        File[] files = dir.listFiles((f, name) -> acceptExtension(name.toLowerCase()));
        imageList.setAll(files);
    }
}
