package com.dexels.navajo.document.databinding;

import com.dexels.navajo.document.Property;

@Deprecated

public interface PropertyDataListener {
	public void propertyDataChanged(Property p, String oldValue, String newValue);

}
