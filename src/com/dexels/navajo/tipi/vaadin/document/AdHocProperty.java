package com.dexels.navajo.tipi.vaadin.document;

import com.vaadin.data.Property;

public class AdHocProperty implements Property {

	private static final long serialVersionUID = -2927504254364457100L;
	private final Class<?> type;
	private Object value;

	public AdHocProperty(Object value, Class<?> type) {
		setValue(value);
		this.type = type;
	}
	
	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object newValue) throws ReadOnlyException, ConversionException {
		this.value = newValue;
	}

	@Override
	public Class<?> getType() {
		return type;
	}

	@Override
	public boolean isReadOnly() {
		return false;
	}

	@Override
	public void setReadOnly(boolean newStatus) {

	}

}
