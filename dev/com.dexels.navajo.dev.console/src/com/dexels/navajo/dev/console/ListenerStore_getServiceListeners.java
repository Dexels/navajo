package com.dexels.navajo.dev.console;

import java.util.Set;

import org.apache.felix.service.command.CommandSession;

import com.dexels.navajo.scheduler.triggers.AfterWebserviceTrigger;
import com.dexels.navajo.server.enterprise.scheduler.Listener;

public class ListenerStore_getServiceListeners extends ListenerStoreCommand {

    public void getlisteners(CommandSession session) {
        getlisteners(session, null);
    }

    public void getlisteners(CommandSession session, String filter) {
        Set<Listener> all = getListenerStore().getListeners(AfterWebserviceTrigger.class);

        for (Listener l : all) {
            AfterWebserviceTrigger cl = (AfterWebserviceTrigger) l;

            if (filter == null || cl.getWebservicePattern().startsWith(filter)) {
                session.getConsole().println(cl.getWebservicePattern() + " - " + cl.getListenerId());
            }
        }
    }

    @Override
    public String showUsage() {
        return "navajo:getlisteners <webservice>";
    }
}
