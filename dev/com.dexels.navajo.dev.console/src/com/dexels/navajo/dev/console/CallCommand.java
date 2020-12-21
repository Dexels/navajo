/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.dev.console;


import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

public class CallCommand implements ConsoleCommand {
	
	private LocalClient localClient;
	
	public void setLocalClient(LocalClient lc) {
		localClient  = lc;
	}

	/**
	 * 
	 * @param lc The localclient to clear
	 */
	public void clearLocalClient(LocalClient lc) {
		localClient  = null;
	}

	
	
	@Descriptor(value = "Call a script without input. It will bypass auth.") 

	public void call(CommandSession session, @Descriptor(value = "The script to call") String scr) {
		String script = scr.replaceAll("/", ".");
		Navajo n = NavajoFactory.getInstance().createNavajo();
		n.addHeader(NavajoFactory.getInstance().createHeader(n, script, "_internal_", "", -1));
				
		try {
			Navajo out = localClient.call(n);
			out.write(session.getConsole());
		} catch (FatalException e) {
			e.printStackTrace(session.getConsole());
		}
	}

	@Override
	public String showUsage() {
		return null;
	}
}
