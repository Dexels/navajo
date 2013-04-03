package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;

import com.dexels.navajo.sharedstore.SharedStoreFactory;
import com.dexels.navajo.sharedstore.SharedStoreSession;

public class SharedStoreCommand {
	
	public SharedStoreSession getSharedStoreSession(CommandSession mySession) {
		SharedStoreSession sss = (SharedStoreSession) mySession.get("sharedstoresession");
		if ( sss == null ) {
			sss = new SharedStoreSession(SharedStoreFactory.getInstance());
			mySession.put("sharedstoresession", sss);
		}
		return sss;
	}
}
