
package ch.imagik.service;

import ch.imagik.model.Folder;
import ch.imagik.service.processor.Processor;
import com.google.common.eventbus.Subscribe;
import ch.imagik.event.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public final class ImageService implements EventSubscriber {
    private static final String[] EXTENSIONS = new String[] {"jpg", "jpeg", "png", "gif"};
    private final Map<File, Image> thumbnailCache = new HashMap<>();
    private Image brokenImage;

    public static ImageService  build() {
        ImageService imageService = new ImageService();
        EventManager.getInstance().register(imageService);
        return imageService;
    }

    private ImageService() {
        try {
            BufferedImage bufferedImage = ImageIO.read(this.getClass().getResource("/ch/imagik/icon/image-broken.png"));
            brokenImage = SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getBrokenImage() {
        return brokenImage;
    }

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
            image.errorProperty().addListener((observable, oldValue, imageError) -> {
                if (imageError) {
                    thumbnailCache.remove(file);
                    EventManager.getInstance().post(new BrokenImageEvent(file));
                }
            });

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
