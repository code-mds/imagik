package ch.imagik.service.processor;

import ij.ImagePlus;
import ch.imagik.model.ResizeInfo;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;

public class ResizeProcessor extends Processor {
    private ResizeInfo info;

    public ResizeProcessor(Map<String,Object> parameters) {
        super(parameters);
        this.info = (ResizeInfo)parameters.get("resizeInfo");
    }



    @Override
    public BufferedImage process() {
        ImagePlus imageToEdit = new ImagePlus("editing image", currentImage);
        if(info.isPercentage()){
            int calculatedWidth=(int)(imageToEdit.getWidth()*info.getPercentage());
            return  imageToEdit.getProcessor().resize(calculatedWidth).getBufferedImage();
        }else{
            return imageToEdit.getProcessor().resize(info.getWidth(),info.getHeight(),true).getBufferedImage();
        }
    }
}
