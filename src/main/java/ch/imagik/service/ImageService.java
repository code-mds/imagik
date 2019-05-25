
package ch.imagik.service;

import ch.imagik.model.Folder;
import ch.imagik.service.processor.Processor;
import com.google.common.eventbus.Subscribe;
import ch.imagik.event.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class ImageService implements EventSubscriber {
    private static final String[] EXTENSIONS = new String[] {"jpg", "jpeg", "png", "gif"};
    private final Map<File, Image> thumbnailCache = new HashMap<>();

    private Image brokenImageIcon;
    private Image appIcon;

    public static ImageService  build() {
        ImageService imageService = new ImageService();
        EventManager.getInstance().register(imageService);
        return imageService;
    }

    private ImageService() {
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
                    // handle broken images
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

    public static Task<Void> createMultiprocessTask(String filterName, List<File> files, Map<String, Object> parameters) {
        int numberOfFiles = files.size();

        return new Task<>() {
            @Override
            protected Void call() {
                try {
                    Processor processor = createProcessor(filterName, parameters);

                    AtomicInteger counter = new AtomicInteger(0);
                    files.parallelStream().forEach(file -> {
                        processor.processAndSave(file);
                        updateMessage(file.getName());
                        updateProgress(counter.incrementAndGet(), numberOfFiles);
                    });
                }catch(Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void succeeded() {
                super.succeeded();
                EventManager.getInstance().post(new FilesChangedEvent(files));
            }
        };

    }

    public static BufferedImage applyFilter(String filterName, BufferedImage image, Map<String, Object> parameters) {
        try {
            Processor processor = createProcessor(filterName, parameters);
            return processor.process(image);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Processor createProcessor(String filterName, Map<String, Object> parameters) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<Processor> processorClass = (Class<Processor>)Class.forName(filterName);
        Constructor<Processor> processorConstructor = processorClass.getConstructor(Map.class);
        return processorConstructor.newInstance(parameters);
    }

    private static boolean acceptExtension(String name) {
        for (String ext : EXTENSIONS) {
            if(name.endsWith(ext))
                return true;
        }
        return false;
    }

    public Image getBrokenImageIcon() {
        if(brokenImageIcon == null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(this.getClass().getResource("/ch/imagik/icon/image-broken.png"));
                brokenImageIcon = SwingFXUtils.toFXImage(bufferedImage, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return brokenImageIcon;
    }

    public Image getAppIcon() {
        if(appIcon == null)
            appIcon = new Image(this.getClass().getResource("/ch/imagik/icon/icon-app.png").toString());
        return appIcon;
    }
}
