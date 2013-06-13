package com.dexels.navajo.script.api;

/**
 * Adapters that implement the Debugable interface support automatic enabling of debug flags when
 * full debug is set for webservice.
 *  
 * @author arjen
 *
 */
public interface Debugable {

	public void setDebug(boolean b);
	public boolean getDebug();
	
}
