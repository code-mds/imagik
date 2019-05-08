package ch.imagik.service.processor;

import ij.ImagePlus;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class RotateLeftProcessor extends Processor {

    public RotateLeftProcessor(BufferedImage currentImage) {
        this.currentImage=currentImage;
    }

    public RotateLeftProcessor(List<File> passedFiles) {
        this.passedFiles=passedFiles;
    }

    @Override
    public BufferedImage process() {
        ImagePlus imageToEdit = new ImagePlus("editing image", currentImage);
        return imageToEdit.getProcessor().rotateLeft().getBufferedImage();
    }
}
