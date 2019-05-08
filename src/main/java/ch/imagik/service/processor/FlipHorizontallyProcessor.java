package ch.imagik.service.processor;

import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class FlipHorizontallyProcessor extends Processor {

    public FlipHorizontallyProcessor(BufferedImage currentImage) {
        this.currentImage=currentImage;
    }

    public FlipHorizontallyProcessor(List<File> passedFiles) {
        this.passedFiles=passedFiles;
    }

    @Override
    public BufferedImage process() {
        ImagePlus imageToEdit = new ImagePlus("editing image", currentImage);
        ImageProcessor currentImageProcessor = imageToEdit.getProcessor();
        currentImageProcessor.flipHorizontal();
        return currentImageProcessor.getBufferedImage();
    }
}
