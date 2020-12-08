/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;

import com.dexels.navajo.sharedstore.SharedStoreSession;

public class SharedStore_cat extends SharedStoreCommand {

	@Descriptor(value = "Show content of a file") 
	public void cat(CommandSession session,@Descriptor(value = "Filename") String fileName) {

		SharedStoreSession sss = getSharedStoreSession(session);
		try {
			session.getConsole().println(sss.cat(fileName)+"");
		} catch (Exception e) {
			session.getConsole().println(e);
		}
	}

	@Override
	public String showUsage() {
		return "cat <filename>";
	}
}
