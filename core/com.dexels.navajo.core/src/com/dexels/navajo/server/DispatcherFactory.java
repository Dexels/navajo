/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.server;

public class DispatcherFactory {

	private static DispatcherInterface instance;

  
	private DispatcherFactory(DispatcherInterface injectedDispatcher) {	
		instance = injectedDispatcher;
	}
	
	public static DispatcherFactory createDispatcher(DispatcherInterface injectedDispatcher) {
		return new DispatcherFactory(injectedDispatcher);
	}
	public static synchronized DispatcherInterface getInstance() {
		return instance;
	}
	public static synchronized  void setInstance(DispatcherInterface dispatcher) {
		instance = dispatcher;
	}


}
