package com.dexels.navajo.document;

import java.beans.PropertyChangeEvent;

public class DocumentPropertyChangeEvent extends PropertyChangeEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7352694985036390594L;
	private Boolean internal = Boolean.FALSE;

	public DocumentPropertyChangeEvent(Object source, String propertyName,
			Object oldValue, Object newValue) {
		this(source, propertyName, oldValue, newValue, false);
	}

	public DocumentPropertyChangeEvent(Object source, String propertyName,
			Object oldValue, Object newValue, Boolean internal) {
		super(source, propertyName, oldValue, newValue);
		this.internal = internal;
	}

	public Boolean getInternal() {
		return internal;
	}

	public void setInternal(Boolean internal) {
		this.internal = internal;
	}
}
