/*
 * This file is part of the Navajo Project.
 *
 * It is subject to the license terms in the COPYING file found in the top-level directory of
 * this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.  No part of the Navajo
 * Project, including this file, may be copied, modified, propagated, or distributed except
 * according to the terms contained in the COPYING file.
 */

package com.dexels.navajo.server.enterprise.statistics;

import java.util.Map;

import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.NavajoListener;
import com.dexels.navajo.events.types.AuditLogEvent;
import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.script.api.Access;

public class DummyStatisticsRunner implements StatisticsRunnerInterface, NavajoListener {

    private static final DummyStatisticsRunner instance;

    static {
        instance = new DummyStatisticsRunner();
        NavajoEventRegistry.getInstance().addListener(AuditLogEvent.class, instance);
    }

    public final static DummyStatisticsRunner getInstance() {
        return instance;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setEnabled(boolean enabled) {}

    @Override
    public void addAccess(Access access, AsyncMappable mappable) {}

    @Override
    public void onNavajoEvent(NavajoEvent event) {}

    @Override
    public int getAuditLevel() {
        return 0;
    }

    @Override
    public void setAuditLevel(int level) {}

    @Override
    public void initialize(String storePath, Map<?, ?> parameters, String storeClass)
            throws Exception {}

}
