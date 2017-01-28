package com.dexels.navajo.mgmt.statistics;

import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.server.mgmt.api.ServerStatisticsProvider;

public class NavajoFastPoolStatistics implements ServerStatisticsProvider {
    private static final String SEPARATOR = "/";
    private TmlScheduler tmlScheduler;

    public void setPriorityTmlScheduler(TmlScheduler sched) {
        this.tmlScheduler = sched;
    }

    public void clearPriorityTmlScheduler(TmlScheduler sched) {
        this.tmlScheduler = null;
    }

    @Override
    public String getKey() {
        return "fastpoolstatus";
    }

    @Override
    public String getValue() {
        String res = "";
        if (tmlScheduler == null) {
            res = "error";
        } else {
            res =     tmlScheduler.getQueue("fastPool").getActiveRequestCount() 
                    + SEPARATOR 
                    + tmlScheduler.getQueue("fastPool").getMaximumActiveRequestCount() 
                    + SEPARATOR
                    + tmlScheduler.getQueue("fastPool").getQueueSize();
        }
        return res;
    }

}
