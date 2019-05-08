package ch.imagik.service.processor;

import ch.imagik.event.EventManager;
import ch.imagik.event.FilesChangedEvent;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class Processor {
    protected BufferedImage currentImage;
    protected List<File> passedFiles;
    abstract BufferedImage process();
    public void multiProcess() {
        for (File currentFile : passedFiles) {
            try {
                currentImage = ImageIO.read(currentFile);
                currentImage=process();
                ImageIO.write(currentImage, FilenameUtils.getExtension(currentFile.getName()), currentFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        EventManager.getInstance().post(new FilesChangedEvent(passedFiles));
    }
}
