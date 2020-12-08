/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.dev.console;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;

import com.dexels.navajo.sharedstore.SharedStoreSession;

public class SharedStore_put extends SharedStoreCommand {

	@Descriptor(value = "Upload contents of a file") 
	public void put(CommandSession session,@Descriptor(value = "Filename") String fileName) {

		SharedStoreSession sss = getSharedStoreSession(session);
		try {
			sss.put(".", fileName);
			session.getConsole().println("Uploaded file: " + fileName + " to " + sss.pwd());
		} catch (Exception e) {
			session.getConsole().println(e);
		}
	}
	
	@Descriptor(value = "Upload contents of a file") 
	public void put(CommandSession session,@Descriptor(value = "Filename") String sourcePath, String fileName) {

		SharedStoreSession sss = getSharedStoreSession(session);
		try {
			sss.put(sourcePath, fileName);
			session.getConsole().println("Uploaded file: " + fileName + " to " + sss.pwd());
		} catch (Exception e) {
			session.getConsole().println(e);
		}
	}
	
	@Descriptor(value = "Upload contents of a file") 
	public void put(CommandSession session,@Descriptor(value = "Filename") String sourcePath, String fileName, String pathName) {

		SharedStoreSession sss = getSharedStoreSession(session);
		try {
			String current = sss.pwd();
			sss.mkdir(pathName);
			sss.cd(pathName);
			sss.put(sourcePath, fileName);
			sss.cd(current);
			session.getConsole().println("Uploaded file: " + fileName + " to " + pathName);
		} catch (Exception e) {
			session.getConsole().println(e);
		}
	}

	@Override
	public String showUsage() {
		return "navajo:put <filename pattern>\n" + 
			   "navajo:put <local source path> <filename pattern>\n" + 
			   "navajo:put <local source path> <filename pattern> <sharedstore destination path>";
	}
}
