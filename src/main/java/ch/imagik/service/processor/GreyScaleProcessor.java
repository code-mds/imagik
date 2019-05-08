package ch.imagik.service.processor;

import ij.ImagePlus;
import ij.process.ImageConverter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class GreyScaleProcessor extends Processor {

    public GreyScaleProcessor(BufferedImage currentImage) {
        this.currentImage=currentImage;
    }

    public GreyScaleProcessor(List<File> passedFiles) {
        this.passedFiles=passedFiles;
    }

    @Override
    public BufferedImage process() {
        ImagePlus imageToEdit = new ImagePlus("editing image", currentImage);
        ImageConverter imageToConvert = new ImageConverter(imageToEdit);
        imageToConvert.convertToGray32();
        return imageToEdit.getBufferedImage();
    }
}
