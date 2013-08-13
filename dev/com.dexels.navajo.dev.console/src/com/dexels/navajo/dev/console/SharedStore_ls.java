package com.dexels.navajo.dev.console;

import java.util.List;
import org.apache.felix.service.command.CommandSession;
import com.dexels.navajo.sharedstore.SharedStoreSession;
import com.dexels.navajo.sharedstore.SharedStoreSessionEntry;

public class SharedStore_ls extends SharedStoreCommand {

	public void ls(CommandSession session) {
		ls(session, null);
	}
	
	public void ls(CommandSession session, String filter) {
		SharedStoreSession sss = getSharedStoreSession(session);

		List<SharedStoreSessionEntry> objects = sss.ls(filter);
		for ( SharedStoreSessionEntry s : objects ) {
			session.getConsole().println(s.getFormattedName());
		}
	}
}
