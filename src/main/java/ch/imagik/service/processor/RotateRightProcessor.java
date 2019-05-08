package ch.imagik.service.processor;

import ij.ImagePlus;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class RotateRightProcessor extends Processor {

    public RotateRightProcessor(BufferedImage currentImage) {
        this.currentImage=currentImage;
    }

    public RotateRightProcessor(List<File> passedFiles) {
        this.passedFiles=passedFiles;
    }

    @Override
    public BufferedImage process() {
        ImagePlus imageToEdit = new ImagePlus("editing image", currentImage);
        return imageToEdit.getProcessor().rotateRight().getBufferedImage();
    }
}
