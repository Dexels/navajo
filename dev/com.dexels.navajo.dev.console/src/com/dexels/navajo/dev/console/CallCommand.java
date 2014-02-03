package com.dexels.navajo.dev.console;


import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.FatalException;
import com.dexels.navajo.script.api.LocalClient;

public class CallCommand extends ConsoleCommand {
	
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

	
	
	// TODO, rewrite to use NavajoContext?
	
	
	@Descriptor(value = "Call a script without input. It will bypass auth.") 

	public void call(CommandSession session, @Descriptor(value = "The script to call") String scr) {
		String script = scr.replaceAll("/", ".");
		Navajo n = NavajoFactory.getInstance().createNavajo();
		n.addHeader(NavajoFactory.getInstance().createHeader(n, script, "", "", -1));
				
		try {
			Navajo out = localClient.call(n);
			out.write(session.getConsole());
		} catch (FatalException e) {
			e.printStackTrace(session.getConsole());
		}
	}

	@Override
	public String showUsage() {
		// TODO Auto-generated method stub
		return null;
	}
}
