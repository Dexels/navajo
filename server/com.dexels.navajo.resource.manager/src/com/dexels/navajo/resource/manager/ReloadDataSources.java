package com.dexels.navajo.resource.manager;

import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;

public class ReloadDataSources {

	private ResourceManager manager;

	public void setResourceManager(ResourceManager r) {
		manager = r;
	}

	/**
	 * @param r
	 *            the resource manager to remove
	 */
	public void removeResourceManager(ResourceManager r) {
		manager = null;
	}

	@Descriptor(value = "Reload all datasources defined in the datasources xml") 
	public void reload(final CommandSession session) {
		if (manager != null) {
			manager.unloadDataSources();
			manager.setupResources();
			session.getConsole().println("Reloaded datasource");

		}
	}

}
