package com.dexels.navajo.server;

import com.dexels.navajo.document.Navajo;

/**
 * This interface can be used to implement a class that can be used to emit
 * events right after the execution of a webservice and BEFORE the execution of the afterwebservice event.
 * 
 * @author arjen
 *
 */
public interface AfterWebServiceEmitter {

	public void emit(Navajo response);
	
}
