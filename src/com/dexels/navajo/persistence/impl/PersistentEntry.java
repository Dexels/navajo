package com.dexels.navajo.persistence.impl;

import java.io.Serializable;

import com.dexels.navajo.persistence.Persistable;

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
