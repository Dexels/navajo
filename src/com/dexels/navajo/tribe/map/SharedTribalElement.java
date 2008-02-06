package com.dexels.navajo.tribe.map;

import java.io.Serializable;

public class SharedTribalElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4266747466983097925L;

	private String id;
	private Object key;
	private Object value;
	
	public SharedTribalElement(String id, Object key, Object value) {
		this.id = id;
		this.key = key;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public Object getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

}
