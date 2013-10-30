package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;

import com.dexels.navajo.server.enterprise.statistics.MetricsManager;

public class NavajoStatusCommand extends SharedStoreCommand {

	public void status(CommandSession session) {
		session.getConsole().println(MetricsManager.getStatus());
	}
	
}
