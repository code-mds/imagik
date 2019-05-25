package ch.imagik.service.processor;

import ij.ImagePlus;
import java.awt.image.BufferedImage;
import java.util.Map;

public class RotateRightProcessor extends Processor {

    public RotateRightProcessor(Map<String,Object> parameters) {
        super(parameters);
    }

    @Override
    public BufferedImage process(BufferedImage currentImage) {
        ImagePlus imageToEdit = new ImagePlus("editing image", currentImage);
        return imageToEdit.getProcessor().rotateRight().getBufferedImage();
    }
}
