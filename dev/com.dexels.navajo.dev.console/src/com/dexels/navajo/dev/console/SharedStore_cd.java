package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;

import com.dexels.navajo.sharedstore.SharedStoreSession;

public class SharedStore_cd extends SharedStoreCommand {

	public void cd(CommandSession session) {
		this.cd(session,null);
	}

	@Descriptor(value = "Change directory in Shared Store") 
	public void cd(CommandSession session,@Descriptor(value = "New directory") String parent) {

		SharedStoreSession sss = getSharedStoreSession(session);
		try {
			sss.cd(parent);
		} catch (Exception e) {
			session.getConsole().println(e);
		}
	}
}
