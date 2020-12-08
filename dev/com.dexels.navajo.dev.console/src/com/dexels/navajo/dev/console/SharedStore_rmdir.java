/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

	@Override
	public String showUsage() {
		return "navajo:rmdir <empty shared store path>";
	}
}
