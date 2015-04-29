package com.dexels.navajo.logclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import com.dexels.navajo.logclient.transport.LogShipper;

public class NavajoLogAppender extends AppenderSkeleton {
    private static final int MAX_LOG_BACKLOG = 5000;

    private LogEventLayout layout;
    private LogShipper shipper;
    private List<LoggingEvent> logEntries;
    private List<String> logs;

    public NavajoLogAppender() {
        layout = new LogEventLayout();
        shipper = new LogShipper();
        logEntries = new ArrayList<>();
        logs = new ArrayList<>();

        // TODO: debug url
        shipper.setRemoteLoggerURL("localhost:9090/NavajoLogger");

        // The code below is run every 5 seconds
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                for (LoggingEvent event : logEntries) {
                    if (logs.size() > MAX_LOG_BACKLOG) {
                        // stop adding new
                        continue;
                    }
                    String formatted = layout.format(event);
                    if (formatted != null) {
                        logs.add(formatted);
                    }

                }
                logEntries.clear();

                List<String> logsToShip = new ArrayList<>(logs);
                for (String logEvent : logsToShip) {
                    try {
                        shipper.ship(logEvent);
                        logs.remove(logEvent);
                    } catch (IOException e) {
                        // Will try again on the next run
                    }
                }

            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void close() {
        layout = null;
    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    @Override
    protected void append(LoggingEvent event) {
        synchronized (this) {
            logEntries.add(event);
        }
    }

}
