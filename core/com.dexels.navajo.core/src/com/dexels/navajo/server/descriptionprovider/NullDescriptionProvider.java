package com.dexels.navajo.server.descriptionprovider;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public class NullDescriptionProvider extends BaseDescriptionProvider {

	@Override
	public void updateProperty(Navajo in, Property element, String locale) {
		// leave all properties alone
	}

	@Override
	public void flushCache() {
	}

	@Override
	public int getCacheSize() {
		return 0;
	}

	@Override
	public void flushUserCache(String user) {
	}

	@Override
	public void deletePropertyContext(String locale, String context) {
	}

	@Override
	public void updateDescription(String locale, String name, String description, String context, String username) {
	}

	@Override
	public void updatePropertyDescription(PropertyDescription pd) {
	}

}
