package com.dexels.navajo.mgmt.statistics;

import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.server.mgmt.api.ServerStatisticsProvider;

public class NavajoDefaultPoolStatistics implements ServerStatisticsProvider {
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
        return "normalpoolstatus";
    }

    @Override
    public String getValue() {
        String res = "";
        if (tmlScheduler == null) {
            res = "error";
        } else {
            res =     tmlScheduler.getDefaultQueue().getActiveRequestCount() 
                    + SEPARATOR 
                    + tmlScheduler.getDefaultQueue().getMaximumActiveRequestCount() 
                    + SEPARATOR
                    + tmlScheduler.getDefaultQueue().getQueueSize();
        }
        return res;
    }

}
