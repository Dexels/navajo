package com.dexels.navajo.mgmt.statistics;

import com.dexels.navajo.script.api.TmlScheduler;
import com.dexels.server.mgmt.api.ServerStatisticsProvider;

public class NavajoFullPoolStatistics implements ServerStatisticsProvider {
    private TmlScheduler tmlScheduler;

    public void setPriorityTmlScheduler(TmlScheduler sched) {
        this.tmlScheduler = sched;
    }

    public void clearPriorityTmlScheduler(TmlScheduler sched) {
        this.tmlScheduler = null;
    }

    @Override
    public String getKey() {
        return "fullpoolstatus";
    }

    @Override
    public String getValue() {
        String res = "";
        if (tmlScheduler == null) {
            res = "error";
        } else {
            res =  tmlScheduler.getSchedulingStatus();
        }
        return res;
    }

}
