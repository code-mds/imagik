package ch.imagik.service.processor;

import ij.ImagePlus;

import java.awt.image.BufferedImage;

public class RotateRightProcessor {
    public static BufferedImage process(BufferedImage currentImage) {
        ImagePlus imageToEdit = new ImagePlus("editing image", currentImage);
        return imageToEdit.getProcessor().rotateRight().getBufferedImage();
    }
}
