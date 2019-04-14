package ch.imagik.event;

import ch.imagik.model.Folder;

public class FolderRefreshEvent implements EventBase {
    private final Folder folder;
    public FolderRefreshEvent(Folder folder) {
        this.folder = folder;
    }
    public Folder getFolder() {
        return folder;
    }
}
