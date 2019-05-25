package ch.imagik.event;

import ch.imagik.model.Folder;

import java.io.File;

public class FolderSelectedEvent implements EventBase, EventLoggable {
    private final Folder folder;

    public FolderSelectedEvent(String dir) {
        this(new File(dir));
    }

    public FolderSelectedEvent(File dir) {
        this.folder = new Folder(dir);
    }

    public Folder getFolder() {
        return folder;
    }

    @Override
    public String toString() {
        return String.format("FolderSelected|%s", folder);
    }
}
