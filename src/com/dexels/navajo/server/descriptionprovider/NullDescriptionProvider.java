package com.dexels.navajo.server.descriptionprovider;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
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

	public Message dumpCacheMessage(Navajo n) throws NavajoException {
		// TODO Auto-generated method stub
		return null;
	}

}
