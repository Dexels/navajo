package com.dexels.navajo.tipi.statistics;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.tipi.TipiContext;

public class StatisticsHandler implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(StatisticsHandler.class);

    private boolean isRunning;
    private long interval = 10000; // standard is 10 seconds
    private Thread t;

    private List<TipiStatistic> stats;
    private TipiContext myContext;


    public StatisticsHandler(TipiContext tipiContext) {
        myContext = tipiContext;
        stats = new ArrayList<TipiStatistic>();
    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
            isRunning = true;
        }
    }

    public void stop() {
        isRunning = false;
        t.interrupt();
        t = null;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                worker();
            } catch (Exception e) {
                logger.error("StatisticsHandler had an exception", e);
            }
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                // okay...
            }

        }
        t = null;
    }

    private void worker() {
        List<TipiStatistic> statscopy = new ArrayList<TipiStatistic>(stats);
        logger.debug("Going to ship {} statistics", statscopy.size());

        Navajo n = NavajoFactory.getInstance().createNavajo();
        for (TipiStatistic stat : statscopy) {
            stat.addStatisticsTo(n);
            stats.remove(stat);
        }

        ClientInterface client = myContext.getClient();
        if (client != null) {
            try {
                client.doSimpleSend(n, "localhost:9090/tipistats", "", "", "", -1, client.getAllowCompression(), false, false);
            } catch (ClientException e) {
                // Give an info message, but otherwise this is not really a major thing
                logger.info("Exception on shipping the TipiStatistics: {}", e);
            }
        }
    }

    public void processTipiEvent(String component, String parentComponent, String eventname, Long duration) {
        stats.add(new TipiEventStatistic(component, parentComponent, eventname, duration));

    }

    public void processTipiInstantiate(String componentid, Long duration, Boolean unhide) {
        stats.add(new TipiInstantiateStatistic(componentid, duration, unhide));

    }

    public TipiContext getMyContext() {
        return myContext;
    }

    public void setMyContext(TipiContext myContext) {
        this.myContext = myContext;
    }

}
