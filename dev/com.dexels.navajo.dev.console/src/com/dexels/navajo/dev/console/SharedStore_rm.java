package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;

import com.dexels.navajo.sharedstore.SharedStoreSession;

public class SharedStore_rm extends SharedStoreCommand {

	public void rm(CommandSession session, String file) {
		SharedStoreSession sss = getSharedStoreSession(session);
		try {
			sss.rm(file);
		} catch (Exception e) {
			session.getConsole().println(e);
		}
	}
	
}
