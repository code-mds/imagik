package ch.imagik.model;

public class ResizeInfo {
    private final int width;
    private final int height;
    private final Integer percentage;
    public ResizeInfo(Integer percentage) {
        this.width = 0;
        this.height = 0;
        this.percentage = percentage;
    }
    public ResizeInfo(int width, int height) {
        this.width = width;
        this.height = height;
        this.percentage = 0;
    }
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Integer getPercentage() {
        return percentage;
    }
    public boolean isPercentage(){
        return percentage>0;
    }
}
