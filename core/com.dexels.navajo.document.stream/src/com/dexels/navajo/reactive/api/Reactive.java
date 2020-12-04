/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.api;

public class Reactive {
	

	private static ReactiveFinder finderInstance;

	public static enum ReactiveItemType {
		REACTIVE_HEADER,REACTIVE_SOURCE,REACTIVE_TRANSFORMER,REACTIVE_MAPPER, REACTIVE_PIPE, REACTIVE_PARTIAL_PIPE, REACTIVE_SCRIPT
	}
	
	public static void setFinderInstance(ReactiveFinder f) {
		Reactive.finderInstance = f;
	}
	
	public static ReactiveFinder finderInstance() {
		if(finderInstance==null) {
			throw new RuntimeException("No finder instance found");
		}
		return finderInstance;
	}
}
