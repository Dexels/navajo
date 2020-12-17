/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;

import com.dexels.navajo.sharedstore.SharedStoreFactory;
import com.dexels.navajo.sharedstore.SharedStoreSession;

public abstract class SharedStoreCommand implements ConsoleCommand {
	
	public SharedStoreSession getSharedStoreSession(CommandSession mySession) {
		SharedStoreSession sss = (SharedStoreSession) mySession.get("sharedstoresession");
		if ( sss == null ) {
			sss = new SharedStoreSession(SharedStoreFactory.getInstance());
			mySession.put("sharedstoresession", sss);
		}
		return sss;
	}
}
