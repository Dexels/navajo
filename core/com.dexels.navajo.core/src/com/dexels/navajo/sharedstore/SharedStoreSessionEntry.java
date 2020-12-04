/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.sharedstore;

public class SharedStoreSessionEntry {

	private final String objectName;
	private final String formattedName;
	
	SharedStoreSessionEntry(final String name, final String formattedName) {
		this.objectName = name;
		this.formattedName = formattedName;
	}
	
	public String getObjectName() {
		return objectName;
	}

	public String getFormattedName() {
		return formattedName;
	}

}
