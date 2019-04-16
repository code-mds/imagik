
package ch.imagik.service;

import ch.imagik.model.Folder;
import com.google.common.eventbus.Subscribe;
import ch.imagik.event.*;
import ij.ImagePlus;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
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
    private static final String[] EXTENSIONS = new String[] {"jpg", "jpeg", "png", "gif"};
    private final Map<File, Image> thumbnailCache = new HashMap<>();

    public static ImageService  build() {
        ImageService imageService = new ImageService();
        EventManager.getInstance().register(imageService);
        return imageService;
    }

    private ImageService() {}

    public static File[] listImages(Folder folder) {
        return folder == null ? new File[0] : folder.listFiles((f, name) -> acceptExtension(name.toLowerCase()));
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void fileChanged(FilesChangedEvent e) {
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

    public static BufferedImage flipHorizontally(BufferedImage currentImage) {
        ImagePlus imageToEdit = new ImagePlus("editing image", currentImage);
        ImageProcessor currentImageProcessor = imageToEdit.getProcessor();
        currentImageProcessor.flipHorizontal();
        return currentImageProcessor.getBufferedImage();
    }

    public static BufferedImage flipVertically(BufferedImage currentImage) {
        ImagePlus imageToEdit = new ImagePlus("editing image", currentImage);
        ImageProcessor currentImageProcessor = imageToEdit.getProcessor();
        currentImageProcessor.flipVertical();
        return currentImageProcessor.getBufferedImage();
    }

    public static BufferedImage greyScale(BufferedImage currentImage) {
        ImagePlus imageToEdit = new ImagePlus("editing image",currentImage);
        ImageConverter imageToConvert = new ImageConverter(imageToEdit);
        imageToConvert.convertToGray32();
        return imageToEdit.getBufferedImage();
    }

    public void multiSelectionImageEdit(List<File> passedFiles, Function<BufferedImage,BufferedImage> serviceSelected){
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

    private static boolean acceptExtension(String name) {
        for (String ext : EXTENSIONS) {
            if(name.endsWith(ext))
                return true;
        }
        return false;
    }
}
