package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;

import com.dexels.navajo.server.enterprise.statistics.MetricsManager;

public class NavajoStatusCommand extends ConsoleCommand {

	public void status(CommandSession session) {
		session.getConsole().println(MetricsManager.getStatus());
	}

	@Override
	public String showUsage() {
		return "Shows status of all active Navajo submodules";
	}
	
}
