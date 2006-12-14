package com.dexels.navajo.server.descriptionprovider;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;

public interface DescriptionProvider {
	public void updatePropertyDescriptions(Navajo in, Navajo out) throws NavajoException;
	public Message dumpCacheMessage(Navajo n) throws NavajoException;
	public int getCacheSize();
	public void flushCache();
	public void flushUserCache(String user);
}
