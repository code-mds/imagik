package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ThumbnailController implements Initializable {
    private final String[] EXTENSIONS = new String[] {"jpg", "jpeg", "png", "gif"};
    private final int THUMB_SIZE = 50;

    @FXML private ListView<File> thumbnails;

    private final ObservableList<File> imageList = FXCollections.observableArrayList();

    public void update(File dir) {
        File[] files = dir.listFiles((f, name) -> acceptExtension(name.toLowerCase()));
        imageList.setAll(files);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        thumbnails.setItems(imageList);

        thumbnails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected item: " + newValue);
        });

        thumbnails.setCellFactory(param -> new ListCell<File>() {
            private ImageView imageView = new ImageView();

            @Override
            public void updateItem(File file, boolean empty) {
                super.updateItem(file, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Image image = getThumbnail(file);
                    imageView.fitHeightProperty().setValue(THUMB_SIZE);
                    imageView.fitWidthProperty().setValue(THUMB_SIZE);
                    imageView.setImage(image);

                    setText(file.getName());
                    setGraphic(imageView);
                }
            }
        });
    }

    private Image getThumbnail(File file) {
        String uri = file.toURI().toString();
        System.out.println(uri);
        return new Image(uri);
    }

    private boolean acceptExtension(String name) {
        for (String ext : EXTENSIONS) {
            if(name.endsWith(ext))
                return true;
        }
        return false;
    }

//    private Image getScaledImage(File file){
//        try {
//            BufferedImage img2;
//
//            BufferedImage sourceImage = ImageIO.read(file);
//            int width = sourceImage.getWidth();
//            int height = sourceImage.getHeight();
//
//            if(width>height){
//                float extraSize = height-100;
//                float percentHight = (extraSize/height)*100;
//                float percentWidth = width - ((width/100)*percentHight);
//                BufferedImage img = new BufferedImage((int)percentWidth, 100, BufferedImage.TYPE_INT_RGB);
//                java.awt.Image scaledImage = sourceImage.getScaledInstance((int)percentWidth, 100, java.awt.Image.SCALE_SMOOTH);
//                img.createGraphics().drawImage(scaledImage, 0, 0, null);
//                img2 = img.getSubimage((int)((percentWidth-100)/2), 0, 100, 100);
//
//            }else{
//                float extraSize=    width-100;
//                float percentWidth = (extraSize/width)*100;
//                float  percentHight = height - ((height/100)*percentWidth);
//                BufferedImage img = new BufferedImage(100, (int)percentHight, BufferedImage.TYPE_INT_RGB);
//                java.awt.Image scaledImage = sourceImage.getScaledInstance(100,(int)percentHight, java.awt.Image.SCALE_SMOOTH);
//                img.createGraphics().drawImage(scaledImage, 0, 0, null);
//                img2 = img.getSubimage(0, (int)((percentHight-100)/2), 100, 100);
//            }
//            Image image = SwingFXUtils.toFXImage(img2, null);
//            return image;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return  null;
//    }
}
