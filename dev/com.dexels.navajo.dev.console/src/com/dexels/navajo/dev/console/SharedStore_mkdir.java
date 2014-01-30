package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;

import com.dexels.navajo.sharedstore.SharedStoreSession;

public class SharedStore_mkdir extends SharedStoreCommand {

	@Descriptor(value = "Path name") 
	public void mkdir(CommandSession session,@Descriptor(value = "Path name") String pathName) {

		SharedStoreSession sss = getSharedStoreSession(session);
		try {
			sss.mkdir(pathName);
			session.getConsole().println("Created directory: " +  sss.pwd());
		} catch (Exception e) {
			session.getConsole().println(e);
		}
	}
}
