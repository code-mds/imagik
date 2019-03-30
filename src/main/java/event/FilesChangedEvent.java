package event;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class FilesChangedEvent implements EventBase {
    private final List<File> files;

    public FilesChangedEvent(List<File> files) {
        this.files = files;
    }

    public List<File> getFiles() {
        return new ArrayList<>(files);
    }
}
