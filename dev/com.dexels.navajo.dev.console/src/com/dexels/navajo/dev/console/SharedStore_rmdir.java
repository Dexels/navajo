package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;

import com.dexels.navajo.sharedstore.SharedStoreSession;

public class SharedStore_rmdir extends SharedStoreCommand {

	@Descriptor(value = "Path name") 
	public void rmdir(CommandSession session,@Descriptor(value = "Path name") String pathName, String...args ) {

		SharedStoreSession sss = getSharedStoreSession(session);
		try {
			boolean force = false;
			for ( String a: args ) {
				if ( a.equals("-f") ) {
					force = true;
				}
			}
			String r = sss.rmdir(pathName, force);
			session.getConsole().println("Removed directory: " +  r);
		} catch (Exception e) {
			session.getConsole().println(e);
		}
	}
}
