package com.dexels.navajo.server.enterprise.descriptionprovider;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;

public interface DescriptionProviderInterface {

	public void updatePropertyDescriptions(Navajo in, Navajo out) throws NavajoException;
//	public Message dumpCacheMessage(Navajo n) throws NavajoException;
	public int getCacheSize();
	public void flushCache();
	public void flushUserCache(String user);
	public void updatePropertyDescription(PropertyDescription pd);
	public void updateDescription(String locale, String name, String description, String context, String username);
	public void deletePropertyContext(String locale, String context);
	public void setDescriptionConfigMessage(Message descriptionMessage);
	
}
