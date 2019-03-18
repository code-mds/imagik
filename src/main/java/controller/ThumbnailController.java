package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ThumbnailController implements Initializable {
    @FXML
    private TilePane tilePane;

    private final String[] EXTENSIONS = new String[] {"jpg", "jpeg", "png", "gif"};
    private final ObservableList<ImageView> imageList = FXCollections.observableArrayList();

    public void update(File dir) {
        File[] files = dir.listFiles((f, name) -> acceptExtension(name.toLowerCase()));
        for (File f : files) {

            System.out.println(f.getAbsolutePath());
                Image img = getScaledImage(f);
                ImageView imageView = new ImageView(img);
                imageList.add(imageView);
                tilePane.getChildren().setAll(imageList);
        }
    }

    private Image getScaledImage(File file){
        try {
            BufferedImage img2 = new BufferedImage(100, 100 ,BufferedImage.TYPE_INT_RGB);

            BufferedImage sourceImage = ImageIO.read(file);
            int width = sourceImage.getWidth();
            int height = sourceImage.getHeight();

            if(width>height){
                float extraSize=    height-100;
                float percentHight = (extraSize/height)*100;
                float percentWidth = width - ((width/100)*percentHight);
                BufferedImage img = new BufferedImage((int)percentWidth, 100, BufferedImage.TYPE_INT_RGB);
                java.awt.Image scaledImage = sourceImage.getScaledInstance((int)percentWidth, 100, java.awt.Image.SCALE_SMOOTH);
                img.createGraphics().drawImage(scaledImage, 0, 0, null);
                img2 = img.getSubimage((int)((percentWidth-100)/2), 0, 100, 100);

            }else{
                float extraSize=    width-100;
                float percentWidth = (extraSize/width)*100;
                float  percentHight = height - ((height/100)*percentWidth);
                BufferedImage img = new BufferedImage(100, (int)percentHight, BufferedImage.TYPE_INT_RGB);
                java.awt.Image scaledImage = sourceImage.getScaledInstance(100,(int)percentHight, java.awt.Image.SCALE_SMOOTH);
                img.createGraphics().drawImage(scaledImage, 0, 0, null);
                img2 = img.getSubimage(0, (int)((percentHight-100)/2), 100, 100);
            }
            Image image = SwingFXUtils.toFXImage(img2, null);
            return image;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return  null;
    }


    private boolean acceptExtension(String name) {
        for (String ext : EXTENSIONS) {
            if(name.endsWith(ext))
                return true;
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //tilePane.setItems(imageMetadataList);
    }
}
