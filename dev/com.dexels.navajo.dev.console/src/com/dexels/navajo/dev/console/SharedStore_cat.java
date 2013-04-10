package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;

import com.dexels.navajo.sharedstore.SharedStoreSession;

public class SharedStore_cat extends SharedStoreCommand {

	@Descriptor(value = "Show content of a file") 
	public void cat(CommandSession session,@Descriptor(value = "Filename") String fileName) {

		SharedStoreSession sss = getSharedStoreSession(session);
		try {
			session.getConsole().println(sss.cat(fileName)+"");
		} catch (Exception e) {
			session.getConsole().println(e);
		}
	}
}
