package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;

public class SharedStore_pwd extends SharedStoreCommand {

	public void pwd(CommandSession session) {
		session.getConsole().println(getSharedStoreSession(session).pwd());
	}
	
}
