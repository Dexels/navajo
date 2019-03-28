package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;

public interface ConsoleCommand {

	public abstract String showUsage();

	public default void help(CommandSession session) {
		session.getConsole().println("Usage:\n" + showUsage());
	}
	
}
