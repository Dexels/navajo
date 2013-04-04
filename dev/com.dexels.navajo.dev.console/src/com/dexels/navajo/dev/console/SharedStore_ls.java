package com.dexels.navajo.dev.console;

import java.util.List;
import org.apache.felix.service.command.CommandSession;
import com.dexels.navajo.sharedstore.SharedStoreSession;

public class SharedStore_ls extends SharedStoreCommand {

	public void ls(CommandSession session) {
		ls(session, null);
	}
	
	public void ls(CommandSession session, String filter) {
		SharedStoreSession sss = getSharedStoreSession(session);

		List<String> objects = sss.ls(filter);
		for ( String s : objects ) {
			session.getConsole().println(s);
		}
	}
}
