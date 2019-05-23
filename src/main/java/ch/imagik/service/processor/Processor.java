package ch.imagik.service.processor;

import ch.imagik.event.EventManager;
import ch.imagik.event.FilesChangedEvent;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class Processor {
    protected BufferedImage currentImage;
    protected final List<File> passedFiles;


    Processor(Map<String,Object> parameters){
        currentImage=(BufferedImage)parameters.get("currentImage");
        passedFiles=(List<File>)parameters.get("selectedFiles");
    }

    public abstract BufferedImage process();
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
