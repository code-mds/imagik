package ch.imagik.service.processor;

import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.awt.image.BufferedImage;
import java.util.Map;

public class FlipVerticallyProcessor extends Processor {

    public FlipVerticallyProcessor(Map<String,Object> parameters) {
        super(parameters);
    }

    @Override
    public BufferedImage process(BufferedImage currentImage) {
        ImagePlus imageToEdit = new ImagePlus("editing image", currentImage);
        ImageProcessor currentImageProcessor = imageToEdit.getProcessor();
        currentImageProcessor.flipVertical();
        return currentImageProcessor.getBufferedImage();
    }
}
