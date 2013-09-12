package com.dexels.navajo.dev.console;

import java.util.Set;

import org.apache.felix.service.command.CommandSession;

import com.dexels.navajo.scheduler.ListenerStore;
import com.dexels.navajo.server.enterprise.statistics.MetricsManager;

public class NavajoStatusCommand extends SharedStoreCommand {

	public void status(CommandSession session) {
		session.getConsole().println(MetricsManager.getStatus());
	}
	
}
