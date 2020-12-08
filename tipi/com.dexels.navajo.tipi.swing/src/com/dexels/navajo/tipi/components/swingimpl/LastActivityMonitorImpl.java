/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.components.core.LastActivityMonitor;
import com.dexels.navajo.tipi.components.core.TipiThread;

public class LastActivityMonitorImpl implements LastActivityMonitor {
    private static final Logger logger = LoggerFactory.getLogger(LastActivityMonitorImpl.class);

    private static final long serialVersionUID = 3797192651654630370L;
    private long lastActivity;

    public LastActivityMonitorImpl() {
        lastActivity = new Date().getTime();
    }
    @Override
    public void threadActivity(Map<TipiThread, String> threadStateMap, TipiThread tt, String state, int queueSize) {
        logger.trace("Updating last activity");
        lastActivity = new Date().getTime(); 
    }

    @Override
    public Integer getInactiveInMinutes() {
        long duration = new Date().getTime() - lastActivity;
        return Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(duration)).intValue();
    }
}
