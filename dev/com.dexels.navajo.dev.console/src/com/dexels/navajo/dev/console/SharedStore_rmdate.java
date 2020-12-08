/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
