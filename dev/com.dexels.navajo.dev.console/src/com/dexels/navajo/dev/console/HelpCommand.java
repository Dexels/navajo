/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.dev.console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.felix.service.command.CommandSession;
import org.osgi.framework.ServiceRegistration;

public class HelpCommand implements ConsoleCommand {

	private final CommandHandler myCommandHandler;
	
	public HelpCommand(CommandHandler ch) {
		myCommandHandler = ch;
	}
	
	public void help(CommandSession session, String command) {
		
		for ( ServiceRegistration<?> sr : myCommandHandler.registeredCommands ) {
			if ( sr.getReference().getProperty("osgi.command.function").equals(command) ) {
				ConsoleCommand cc = (ConsoleCommand) myCommandHandler.bundleContext.getService(sr.getReference());
				cc.help(session);
			}
		}
	}
	
	@Override
	public void help(CommandSession session) {
		session.getConsole().println("Available commands ( type help <command> for more info):");
		List<String> all = new ArrayList<String>();
		for ( ServiceRegistration<?> sr : myCommandHandler.registeredCommands ) {
			all.add((String) sr.getReference().getProperty("osgi.command.function"));
		}
		Collections.sort(all);
		for ( String s : all ) {
			session.getConsole().println(s);
		}
	}
	
	@Override
	public String showUsage() {
		return "navajo:help | navajo:help <command>";
	}

}
