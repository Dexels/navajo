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

	public void flushUserCache(String user) {
		// TODO Auto-generated method stub
		
	}

	public void updateDescription(String locale, String name, String description, String context) {
		// TODO Auto-generated method stub
		
	}

	public void deletePropertyContext(String locale, String context) {
		// TODO Auto-generated method stub
		
	}

	public void updateDescription(String locale, String name, String description, String context, String username) {
		// TODO Auto-generated method stub
		
	}

}
