package com.dexels.navajo.mapping;

/**
 * Interface to define dependent resource templates.
 * Used in HasDependentResources objects to specify what bean attributes contain dependent resources (like databases, urls, etc.)
 * 
 * @author arjen
 *
 */
public interface DependentResource {

	public String getType();
	public String getValue();
	
}
