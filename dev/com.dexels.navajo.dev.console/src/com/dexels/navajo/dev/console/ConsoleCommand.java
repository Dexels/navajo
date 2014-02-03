package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;

public abstract class ConsoleCommand {

	public abstract String showUsage();

	public void help(CommandSession session) {
		session.getConsole().println("Usage:\n" + showUsage());
	}
	
}
