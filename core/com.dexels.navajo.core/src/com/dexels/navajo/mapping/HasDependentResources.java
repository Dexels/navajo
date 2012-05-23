package com.dexels.navajo.mapping;

public interface HasDependentResources {

	/**
	 * Return a string array of all fields that are used for dependencies on external resources, e.g.
	 * a mail server, a database, another web service, an external web service, an external url, etc.
	 * @return
	 */
	public DependentResource []  getDependentResourceFields();
	
}
