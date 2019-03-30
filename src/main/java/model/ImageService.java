
package model;

import com.google.common.eventbus.Subscribe;
import event.*;
import ij.ImagePlus;
import ij.process.ImageConverter;

import javafx.scene.image.Image;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;


public final class ImageService implements EventSubscriber {

    //TODO handle invalidation of edited images
    private final Map<File, Image> thumbnailCache = new HashMap<>();

    static ImageService build() {
        ImageService imageService = new ImageService();
        EventManager.getInstance().register(imageService);
        return imageService;
    }

    private ImageService() {}

    @Subscribe
    public void fileChanged(FilesChangedEvent e) {
        for (File file : e.getFiles())
            thumbnailCache.remove(file);
    }

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

    public static BufferedImage rotateLeft(BufferedImage currentImage) {
        ImagePlus imageToEdit = new ImagePlus("editing image", currentImage);
        return imageToEdit.getProcessor().rotateLeft().getBufferedImage();
    }

    public static BufferedImage rotateRight(BufferedImage currentImage){
        ImagePlus imageToEdit = new ImagePlus("editing image",currentImage);
        return imageToEdit.getProcessor().rotateRight().getBufferedImage();
    }

    public static BufferedImage greyScale(BufferedImage currentImage) {
        ImagePlus imageToEdit = new ImagePlus("editing image",currentImage);
        ImageConverter imageToConvert = new ImageConverter(imageToEdit);
        imageToConvert.convertToGray32();
        return imageToEdit.getBufferedImage();
    }

    public void multiSelectionImageEditor(List<File> passedFiles, Function<BufferedImage,BufferedImage> serviceSelected){
        for (File currentFile : passedFiles) {
            try {
                BufferedImage currentImage = serviceSelected.apply(ImageIO.read(currentFile));
                ImageIO.write(currentImage, FilenameUtils.getExtension(currentFile.getName()), currentFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        EventManager.getInstance().post(new FilesChangedEvent(passedFiles));
    }
}
