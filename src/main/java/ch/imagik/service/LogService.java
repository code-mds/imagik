package ch.imagik.service;

import ch.imagik.event.*;
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
    private void log(EventLoggable e) {
        output.println("LOG: " + e);
    }
}
