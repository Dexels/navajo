package com.dexels.navajo.server.descriptionprovider;

import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;


public abstract class CachedDescriptionProvider extends BaseDescriptionProvider {

	protected final Map cache = new HashMap();
	
	protected String getDescription(String locale, String username, String webservice, String propertyName){
		String key = locale+"|"+username+"|"+webservice+"|"+propertyName;
		String value = (String)cache.get(key);
		if (value!=null) {
			if (!"%".equals(value)) {
				return value;
			} else {
				// anti-cache found, continue level2
				return getDescription(locale,webservice, propertyName);

			}
		}
		// cache miss, level1
		String missed = retrieveDescription(locale,username,webservice,propertyName);
		if (missed==null) {
			// add an anti-cache
			cache.put(key, "%");
			return getDescription(locale,webservice, propertyName);
		}
		cache.put(key, missed);
		return missed;
	}


	protected String getDescription(String locale, String webservice, String propertyName){
		String key = locale+"|"+webservice+"|"+propertyName;
		String value = (String)cache.get(key);
		if (value!=null) {
			if (!"%".equals(value)) {
				return value;
			} else {
				// anti-cache found, continue level3
				return getDescription(locale,propertyName);

			}
		}
		// cache miss, level2
		String missed = retrieveDescription(locale,webservice,propertyName);
		if (missed==null) {
			// add an anti-cache
			cache.put(key, "%");
			return getDescription(locale,propertyName);
		}
		cache.put(key, missed);
		return missed;
		}


	protected String getDescription(String locale, String propertyName){
		String key = locale+"|"+propertyName;
		String value = (String)cache.get(key);
		if ("%".equals(value)) {
			return null;
		}
		if (value!=null) {
			return value;
		}
		String desc =  retrieveDescription(locale,propertyName);
		if (desc==null) {
			cache.put(key, "%");
			return null;			
		}
		cache.put(key, desc);
		return desc;
		
	}


	protected abstract String retrieveDescription(String locale, String username, String webservice, String propertyName);
	protected abstract String retrieveDescription(String locale, String webservice, String propertyName);
	protected abstract String retrieveDescription(String locale, String propertyName);
	

	public void flushCache() {
		cache.clear();
		
	}

	public int getCacheSize() {
		return cache.size();
	}

}
