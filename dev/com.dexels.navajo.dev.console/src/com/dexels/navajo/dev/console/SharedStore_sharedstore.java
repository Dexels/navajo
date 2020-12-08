/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;

import com.dexels.navajo.sharedstore.SharedStoreSession;

public class SharedStore_sharedstore extends SharedStoreCommand {

	public void sharedstore(CommandSession session) {
		
		SharedStoreSession sss = getSharedStoreSession(session);
		session.getConsole().println(sss.showImplementation());
		
	}

	@Override
	public String showUsage() {
		return "navajo:sharedstore";
	}
}
