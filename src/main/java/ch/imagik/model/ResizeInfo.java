package ch.imagik.model;

public class ResizeInfo {
    private final double width;
    private final double height;
    private final Integer percentage;
    public ResizeInfo(Integer percentage) {
        this.width = 0;
        this.height = 0;
        this.percentage = percentage;
    }
    public ResizeInfo(double width, double height) {
        this.width = width;
        this.height = height;
        this.percentage = 0;
    }
    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public Integer getPercentage() {
        return percentage;
    }
    public boolean isPercentage(){
        return percentage>0;
    }
}
