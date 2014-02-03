package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;

import com.dexels.navajo.sharedstore.SharedStoreSession;

public class SharedStore_get extends SharedStoreCommand {

	@Descriptor(value = "Download contents of a file") 
	public void get(CommandSession session,@Descriptor(value = "Filename") String fileName) {

		SharedStoreSession sss = getSharedStoreSession(session);
		try {
			String result = sss.get(fileName, ".");
			session.getConsole().println(result);
		} catch (Exception e) {
			session.getConsole().println(e);
		}
	}
	
	@Descriptor(value = "Download contents of a file") 
	public void get(CommandSession session,@Descriptor(value = "Filename") String fileName, String destination) {

		SharedStoreSession sss = getSharedStoreSession(session);
		try {
			String result = sss.get(fileName, destination);
			session.getConsole().println(result);
		} catch (Exception e) {
			session.getConsole().println(e);
		}
	}
	
	@Descriptor(value = "Download contents of a file") 
	public void get(CommandSession session,@Descriptor(value = "Filename") String source, String fileName, String destination) {

		SharedStoreSession sss = getSharedStoreSession(session);
		try {
			String current = sss.pwd();
			sss.mkdir(source);
			sss.cd(source);
			String result = sss.get(fileName, destination);
			sss.cd(current);
			session.getConsole().println(result);
		} catch (Exception e) {
			session.getConsole().println(e);
		}
	}

	@Override
	public String showUsage() {
		return "get <filename pattern>\n" +
	           "get <filename pattern> <local destination path>\n" + 
			   "get <shared store source path> <filename pattern> <local destination path>";
	}
}
