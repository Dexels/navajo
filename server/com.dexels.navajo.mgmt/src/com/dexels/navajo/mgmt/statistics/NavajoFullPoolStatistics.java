/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
