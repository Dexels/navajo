package com.dexels.navajo.mapping;

import com.dexels.navajo.mapping.compiler.meta.AdapterFieldDependency;

/**
 * Interface to define dependent resource templates.
 * Used in HasDependentResources objects to specify what bean attributes contain dependent resources (like databases, urls, etc.)
 * 
 * @author arjen
 *
 */
public interface DependentResource {

	/**
	 * Return the type of dependent resource.
	 * 
	 * @return
	 */
	public String getType();
	
	/**
	 * value return the name of the field that contains a references to dependent resource(s).
	 * 
	 * @return
	 */
	public String getValue();
	
	public Class<? extends AdapterFieldDependency> getDependencyClass();
	
	
}
