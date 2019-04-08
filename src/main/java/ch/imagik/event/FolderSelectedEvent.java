package ch.imagik.event;

import java.io.File;

public class FolderSelectedEvent implements EventBase {
    private final File folder;
    public FolderSelectedEvent(File folder) {
        this.folder = folder;
    }

    public File getFolder() {
        return folder;
    }
}
