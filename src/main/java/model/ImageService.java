
package model;

import ij.ImagePlus;
import ij.process.ImageConverter;

import javafx.scene.image.Image;

import java.awt.image.BufferedImage;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;


public class ImageService {

    //TODO handle invalidation of edited images

    private final Map<File, Image> thumbnailCache = new HashMap<>();

    public Image getThumbnail(File file, int thumbSize) {
        Image image;
        if(!thumbnailCache.containsKey(file)) {
            String uri = file.toURI().toString();
            image = new Image(uri, thumbSize, thumbSize, true, false, true);
            thumbnailCache.put(file, image);
        } else {
            image = thumbnailCache.get(file);
        }
        return image;
    }
    public BufferedImage rotateLeft(BufferedImage currentImage) {
        ImagePlus imageToEdit = new ImagePlus("editing image", currentImage);
        return imageToEdit.getProcessor().rotateLeft().getBufferedImage();
    }
    public BufferedImage rotateRight(BufferedImage currentImage){
        ImagePlus imageToEdit = new ImagePlus("editing image",currentImage);
        return imageToEdit.getProcessor().rotateRight().getBufferedImage();
    }
    public BufferedImage greyScale(BufferedImage currentImage) {
        ImagePlus imageToEdit = new ImagePlus("editing image",currentImage);
        ImageConverter imageToConvert = new ImageConverter(imageToEdit);
        imageToConvert.convertToGray32();
        return imageToEdit.getBufferedImage();
    }
    public List<BufferedImage> serviceParser(List<BufferedImage> passedImages, Function<BufferedImage,BufferedImage> serviceSelected){
        List<BufferedImage> currentImages = new ArrayList<BufferedImage>();
        for(int i=0;i<passedImages.size();i++){
            currentImages.add(serviceSelected.apply(passedImages.get(i)));
        }
        return currentImages;

    }
}
