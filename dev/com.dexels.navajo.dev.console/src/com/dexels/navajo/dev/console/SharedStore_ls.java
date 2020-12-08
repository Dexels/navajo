/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

	@Override
	public String showUsage() {
		return "navajo:ls [<file name pattern>]";
	}
}
