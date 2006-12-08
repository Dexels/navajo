package com.dexels.navajo.server.descriptionprovider;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public class NullDescriptionProvider extends BaseDescriptionProvider {

	public void updateProperty(Navajo in, Property element, String locale) {
		// leave all properties alone
	}

	public void flushCache() {
		// TODO Auto-generated method stub
		
	}

	public int getCacheSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
