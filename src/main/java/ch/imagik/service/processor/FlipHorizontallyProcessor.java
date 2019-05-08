package ch.imagik.service.processor;

import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;

public class FlipHorizontallyProcessor extends Processor {

    public FlipHorizontallyProcessor(Map<String,Object> parameters) {
        super(parameters);
    }

    @Override
    public BufferedImage process() {
        ImagePlus imageToEdit = new ImagePlus("editing image", currentImage);
        ImageProcessor currentImageProcessor = imageToEdit.getProcessor();
        currentImageProcessor.flipHorizontal();
        return currentImageProcessor.getBufferedImage();
    }
}
