package ch.imagik.service.processor;

import ij.ImagePlus;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;

public class RotateRightProcessor extends Processor {

    public RotateRightProcessor(Map<String,Object> parameters) {
        super(parameters);
    }

    @Override
    public BufferedImage process() {
        ImagePlus imageToEdit = new ImagePlus("editing image", currentImage);
        return imageToEdit.getProcessor().rotateRight().getBufferedImage();
    }
}
