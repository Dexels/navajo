package com.dexels.navajo.dev.console;

import java.util.Date;

import org.apache.felix.service.command.CommandSession;

import com.dexels.navajo.sharedstore.SharedStoreSession;

public class SharedStore_rmdate extends SharedStoreCommand {

	public void rmdate(CommandSession session, String date) {
		SharedStoreSession sss = getSharedStoreSession(session);
		Long l = Long.valueOf(date);
		try {
			sss.rmOlderThan(new Date(l));
		} catch (Exception e) {
			session.getConsole().println(e);
		}
	}

	@Override
	public String showUsage() {
		return "navajo:rmdate <date in long>";
	}
	
}
