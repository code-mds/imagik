package ch.imagik.model;

public class ResizeInfo {
    private final double width;
    private final double height;

    public ResizeInfo(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}
