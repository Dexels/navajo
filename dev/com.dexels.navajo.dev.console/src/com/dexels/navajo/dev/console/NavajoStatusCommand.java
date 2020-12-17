/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;

import com.dexels.navajo.server.enterprise.statistics.MetricsManager;

public class NavajoStatusCommand implements ConsoleCommand {

	public void status(CommandSession session) {
		session.getConsole().println(MetricsManager.getStatus());
	}

	@Override
	public String showUsage() {
		return "Shows status of all active Navajo submodules";
	}
	
}
