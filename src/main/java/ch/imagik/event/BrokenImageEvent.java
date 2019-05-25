package ch.imagik.event;

import java.io.File;

public class BrokenImageEvent implements EventBase {
    private final File file;
    public BrokenImageEvent(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
