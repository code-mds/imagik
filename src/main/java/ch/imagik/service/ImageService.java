
package ch.imagik.service;

import ch.imagik.model.Folder;
import ch.imagik.model.ResizeInfo;
import ch.imagik.service.processor.Processor;
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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
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

    public static BufferedImage applyFilter(String filterName, Map<String, Object> parameters) {
        try {
            Class<Processor> processorClass = (Class<Processor>)Class.forName(filterName);
            Constructor<Processor> processorConstructor = processorClass.getConstructor(Map.class);
            Processor processor = processorConstructor.newInstance(parameters);
            if(parameters.containsKey("currentImage")) {
                return processor.process();
            }else{
                processor.multiProcess();
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean acceptExtension(String name) {
        for (String ext : EXTENSIONS) {
            if(name.endsWith(ext))
                return true;
        }
        return false;
    }
}
