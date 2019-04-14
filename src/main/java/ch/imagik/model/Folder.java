package ch.imagik.model;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Date;

final public class Folder {
    private final File fs;
    private final Date loadTime;

    public File getFile() {
        return fs;
    }

    public Folder(File fs) {
        this.fs = fs;
        loadTime = new Date();
    }

    @Override
    public boolean equals(Object obj)
    {
        if ((obj instanceof Folder)) {
            Folder other = (Folder)obj;
            return fs.equals(other.fs) && loadTime.equals(other.loadTime);
        }
        return false;
    }

    public int hashCode() {
        return fs.hashCode();
    }

    @Override
    public String toString() {
        return fs.toString();
    }

    public File[] listFiles(FilenameFilter filter) {
        return fs.listFiles(filter);
    }
}
