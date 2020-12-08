/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.impl;

import com.dexels.navajo.reactive.CoreReactiveFinder;
import com.dexels.navajo.reactive.api.Reactive;

public class OSGiReactiveFinder extends CoreReactiveFinder {

	
	public void activate() {
		//
		Reactive.setFinderInstance(this);
	}
	
	public void deactivate() {
		Reactive.setFinderInstance(null);
	}
}
