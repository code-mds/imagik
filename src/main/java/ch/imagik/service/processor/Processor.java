package ch.imagik.service.processor;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class Processor {
    protected Map<String,Object> parameters;
    Processor(Map<String,Object> parameters) {
        this.parameters = parameters;
    }

    public abstract BufferedImage process(BufferedImage image);

    public void multiProcess(List<File> selectedFiles) {
        // leverage parallel processing and notify user at the end
        selectedFiles.parallelStream().forEach(this::processAndSave);
    }

    public void processAndSave(File currentFile) {
        try {
            BufferedImage bufferedImage = ImageIO.read(currentFile);
            bufferedImage = process(bufferedImage);
            ImageIO.write(bufferedImage, FilenameUtils.getExtension(currentFile.getName()), currentFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
