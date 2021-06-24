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
import java.util.Set;

import com.dexels.navajo.events.types.AuditLogEvent;
import com.dexels.navajo.events.types.WorkflowEvent;
import com.dexels.navajo.events.types.WorkflowLog;
import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.server.statistics.impl.TodoItem;

public interface StoreInterface {

    /**
     * Stores an access object in the (persistent) Navajo store.
     * It doesn't seem to be used. Is it deprecated?
     */
    public void storeAccess(Access access, AsyncMappable mappable);

    /**
     * Stores multiple access objects in the (persistent) Navajo store. Returns number of
     * written full access logs.
     */
    public int storeAccess(Map<String, TodoItem> accessMap);

    public void storeAuditLogs(final Set<AuditLogEvent> events);

    public void storeWorkflowEvents(final Set<WorkflowEvent> events);

    public void storeWorkflowLogs(final Set<WorkflowLog> entries);

    /**
     * Clean up and consolidate action - to be called after completing inserting statistics data.
     */
    public void consolidate();

    /**
     * Set the url for the database.
     */
    public void setDatabaseUrl(String url);

    /**
     * Pass database specific parameters in a map object.
     */
    public void setDatabaseParameters(Map<String, String> parameters);

    public void initialize();

    public void shutdown();

}
