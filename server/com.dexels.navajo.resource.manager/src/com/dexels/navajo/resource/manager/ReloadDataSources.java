package com.dexels.navajo.resource.manager;

import org.apache.felix.service.command.CommandSession;

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

	public void reload(final CommandSession session) {
		if (manager != null) {
			manager.setupResources();
			session.getConsole().println("Reloaded datasource");

		}
	}

}
