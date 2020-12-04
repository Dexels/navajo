/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.persistence.impl;

import java.io.Serializable;

public class PersistentEntry implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3339195823232547197L;
	
	//private Persistable document;
	private String keyValues = "";
	private String service;
	
	public PersistentEntry(String service) {
		//this.document = p;
		this.service = service;
	}

	public String getKeyValues() {
		return keyValues;
	}

	public void setKeyValues(String keyValues) {
		this.keyValues = keyValues;
	}

//	public Persistable getDocument() {
//		return document;
//	}

	public String getService() {
		return service;
	}
	
	
	
}
