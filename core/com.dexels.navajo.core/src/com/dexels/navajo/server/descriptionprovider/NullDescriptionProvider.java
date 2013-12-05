package com.dexels.navajo.server.descriptionprovider;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public class NullDescriptionProvider extends BaseDescriptionProvider {

	public void updateProperty(Navajo in, Property element, String locale) {
		// leave all properties alone
	}

	public void flushCache() {
	}

	public int getCacheSize() {
		return 0;
	}

	public void flushUserCache(String user) {
	}

	public void deletePropertyContext(String locale, String context) {
	}

	public void updateDescription(String locale, String name, String description, String context, String username) {
	}

	public void updatePropertyDescription(PropertyDescription pd) {
	}

}
