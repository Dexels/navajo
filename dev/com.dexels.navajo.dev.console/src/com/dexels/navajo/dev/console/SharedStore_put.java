package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;

import com.dexels.navajo.sharedstore.SharedStoreSession;

public class SharedStore_put extends SharedStoreCommand {

	@Descriptor(value = "Upload contents of a file") 
	public void put(CommandSession session,@Descriptor(value = "Filename") String fileName) {

		SharedStoreSession sss = getSharedStoreSession(session);
		try {
			sss.put(fileName);
			session.getConsole().println("Uploaded file: " + fileName + " to " + sss.pwd());
		} catch (Exception e) {
			session.getConsole().println(e);
		}
	}
	
	@Descriptor(value = "Upload contents of a file") 
	public void put(CommandSession session,@Descriptor(value = "Filename") String fileName, String pathName) {

		SharedStoreSession sss = getSharedStoreSession(session);
		try {
			String current = sss.pwd();
			sss.mkdir(pathName);
			sss.cd(pathName);
			sss.put(fileName);
			sss.cd(current);
			session.getConsole().println("Uploaded file: " + fileName + " to " + pathName);
		} catch (Exception e) {
			session.getConsole().println(e);
		}
	}
}
