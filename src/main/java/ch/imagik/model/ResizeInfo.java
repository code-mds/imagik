package ch.imagik.model;

public class ResizeInfo {
    private final int width;
    private final int height;
    private final double percentage;
    public ResizeInfo(Integer percentage) {
        this.width = 0;
        this.height = 0;
        this.percentage = percentage/100.0;
    }
    public ResizeInfo(int width, int height) {
        this.width = width;
        this.height = height;
        this.percentage = 0.0;
    }
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public double getPercentage() {
        return percentage;
    }
    public boolean isPercentage(){
        return percentage>0;
    }
}
