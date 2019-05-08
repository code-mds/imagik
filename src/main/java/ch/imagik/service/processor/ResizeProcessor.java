package ch.imagik.service.processor;

import ij.ImagePlus;
import ch.imagik.model.ResizeInfo;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class ResizeProcessor extends Processor {
    private ResizeInfo info;

    public ResizeProcessor(BufferedImage currentImage,ResizeInfo info) {
        this.currentImage=currentImage;
        this.info=info;
    }
    public ResizeProcessor(List<File> passedFiles,ResizeInfo info) {
        this.passedFiles=passedFiles;
        this.info=info;
    }

    @Override
    BufferedImage process() {
        ImagePlus imageToEdit = new ImagePlus("editing image", currentImage);
        if(info.isPercentage()){
            return  imageToEdit.getProcessor().resize(imageToEdit.getWidth()*info.getPercentage()).getBufferedImage();
        }else{
            return imageToEdit.getProcessor().resize(info.getWidth(),info.getHeight(),true).getBufferedImage();
        }
    }
}
