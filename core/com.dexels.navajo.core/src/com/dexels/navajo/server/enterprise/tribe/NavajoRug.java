package com.dexels.navajo.server.enterprise.tribe;

import java.io.Serializable;

import com.dexels.navajo.document.Navajo;

/**
 * Interface to wrap Navajo into some container for transportation purposes.
 * 
 * @author arjenschoneveld
 *
 */
public interface NavajoRug extends Serializable {

	public Navajo getNavajo();
	
}
