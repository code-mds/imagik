package ch.imagik.service.processor;

import ij.ImagePlus;
import ij.process.ImageConverter;

import java.awt.image.BufferedImage;
import java.util.Map;

public class GreyScaleProcessor extends Processor {

    public GreyScaleProcessor(Map<String,Object> parameters) {
        super(parameters);
    }

    @Override
    public BufferedImage process(BufferedImage currentImage) {
        ImagePlus imageToEdit = new ImagePlus("editing image", currentImage);
        ImageConverter imageToConvert = new ImageConverter(imageToEdit);
        imageToConvert.convertToGray32();
        return imageToEdit.getBufferedImage();
    }
}
