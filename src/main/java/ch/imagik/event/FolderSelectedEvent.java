package ch.imagik.event;

import ch.imagik.model.Folder;

public class FolderSelectedEvent implements EventBase {
    private final Folder folder;
    public FolderSelectedEvent(Folder folder) {
        this.folder = folder;
    }
    public Folder getFolder() {
        return folder;
    }
}
