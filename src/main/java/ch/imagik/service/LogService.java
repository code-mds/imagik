package ch.imagik.service;

import ch.imagik.event.EventBase;
import ch.imagik.event.EventManager;
import ch.imagik.event.EventSubscriber;
import ch.imagik.event.FolderSelectedEvent;
import com.google.common.eventbus.Subscribe;
import java.io.PrintStream;

public final class LogService implements EventSubscriber {
    private final PrintStream output;

    private LogService(PrintStream outputStream) {
        this.output = outputStream;
    }

    public static LogService build(PrintStream out) {
        LogService logService = new LogService(out);
        EventManager.getInstance().register(logService);
        return logService;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Subscribe
    private void folderSelected(FolderSelectedEvent e) {
        log(e);
    }

    void log(EventBase e) {
        output.println("LOG: " + e);
    }

}
