/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.mail.functions;

import java.io.InputStream;
import java.util.List;

import navajo.ExtensionDefinition;



public class MailFunctionDefinition implements ExtensionDefinition {

	private static final long serialVersionUID = -3429274998043371128L;
	private transient ClassLoader extensionClassLoader = null;

	@Override
	public InputStream getDefinitionAsStream() {
		return getClass().getClassLoader().getResourceAsStream("com/dexels/navajo/tipi/mail/functions/mailfunctions.xml");
	}

	@Override
	public String getConnectorId() {
		return null;
	}

	@Override
	public List<String> getDependingProjectUrls() {
		// list urls to open source projects here
		return null;
	}

	public String getDeploymentDescriptor() {
		return null;
	}

	@Override
	public String getDescription() {
		
		return "Mail interaction navajo function library";
	}

	@Override
	public String getId() {
		return "tipimail";
	}

	@Override
	public String[] getIncludes() {
		return new String[]{"com/dexels/navajo/functions/mailfunctions.xml"};
	}

	public List<String> getLibraryJars() {
		return null;
	}


	@Override
	public String getProjectName() {
		return "com.dexels.navajo.tipi.mail";
	}

	@Override
	public List<String> getRequiredExtensions() {
		return null;
	}

	@Override
	public boolean isMainImplementation() {
		return false;
	}

	@Override
	public String requiresMainImplementation() {
		// any will do
		return null;
	}
	@Override
	public ClassLoader getExtensionClassloader() {
		return extensionClassLoader;
	}

	@Override
	public void setExtensionClassloader(ClassLoader extClassloader) {
		extensionClassLoader =  extClassloader;
	}



}
