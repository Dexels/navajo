package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;

import com.dexels.navajo.sharedstore.SharedStoreSession;

public class SharedStore_sharedstore extends SharedStoreCommand {

	public void sharedstore(CommandSession session) {
		
		SharedStoreSession sss = getSharedStoreSession(session);
		session.getConsole().println(sss.showImplementation());
		
	}
}
