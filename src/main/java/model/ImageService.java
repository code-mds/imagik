package model;

import ij.ImagePlus;
import ij.process.ImageConverter;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ImageService {
    private final Map<File, Image> imageCache = new HashMap<>();

    public Image getJavaFXImage(File file) {
        Image image;
        if(!imageCache.containsKey(file)) {
            image = load(file);
            imageCache.put(file, image);
        } else {
            image = imageCache.get(file);
        }
        return image;
    }

    public BufferedImage greyScale(BufferedImage currentImage) {
        ImagePlus imageToEdit = new ImagePlus("editing image",currentImage);
        ImageConverter imageToConvert = new ImageConverter(imageToEdit);
        imageToConvert.convertToGray32();
        return imageToEdit.getBufferedImage();
    }

    private Image load(File file) {
        String uri = file.toURI().toString();
        return new Image(uri);
    }
}
