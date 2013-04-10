package com.dexels.navajo.dev.console;

import java.io.File;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;

import com.dexels.navajo.sharedstore.SharedStoreSession;

public class SharedStore_get extends SharedStoreCommand {

	@Descriptor(value = "Download contents of a file") 
	public void get(CommandSession session,@Descriptor(value = "Filename") String fileName) {

		SharedStoreSession sss = getSharedStoreSession(session);
		try {
			File f = sss.get(fileName, fileName);
			session.getConsole().println("Downloaded file: " + fileName + " to " + f.getAbsolutePath());
		} catch (Exception e) {
			session.getConsole().println(e);
		}
	}
}
