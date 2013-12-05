package com.dexels.navajo.server.descriptionprovider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;


public abstract class CachedDescriptionProvider extends BaseDescriptionProvider {

	protected final Map<String,String> cache = new HashMap<String,String>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(CachedDescriptionProvider.class);
	
	protected boolean debug = false;
	public Message dumpCacheMessage(Navajo n) throws NavajoException {
		Message m = NavajoFactory.getInstance().createMessage(n,"Cache",Message.MSG_TYPE_ARRAY);
		for (Iterator<String> iter = cache.keySet().iterator(); iter.hasNext();) {
			String element = iter.next();
			String value = cache.get(element);
			Property keyProp = NavajoFactory.getInstance().createProperty(n, "Key", Property.STRING_PROPERTY, element, 521, "", Property.DIR_IN);
			Property valProp = NavajoFactory.getInstance().createProperty(n, "Value", Property.STRING_PROPERTY, value, 521, "", Property.DIR_IN);
			Message ii = NavajoFactory.getInstance().createMessage(n, "Cache", Message.MSG_TYPE_ARRAY_ELEMENT);
			m.addMessage(ii);
			ii.addProperty(keyProp);
			ii.addProperty(valProp);
		}
		return m;
	}
	
	protected String getDescription(String locale, String username, String webservice, String propertyName){
		String key = locale+"|"+username+"|"+webservice+"|"+propertyName;
		if (debug) {
			logger.debug("Getting level1: "+key);
		}
		String value = cache.get(key);
		if (value!=null) {
			if (!"%".equals(value)) {
				if (debug) {
					logger.debug("Cache hit, level1. "+value);
				}
				return value;
			} else {
				// anti-cache found, continue level2
					if (debug) {
						logger.debug("No username, no webservice. Heading for level1b. "+key);
					}
					
				return  getDescriptionUser(locale, username, webservice, propertyName);

			}
		}
		// cache miss, level1

//		if (webservice==null) {
//			return  getDescriptionUser(locale, username, webservice, propertyName);
//			missed = retrieveDescriptionWithUsernameWithoutService(locale,username,propertyName);
//			
//			if (missed==null) {
//				// add an anti-cache
//				cache.put(key, "%");
//				return getDescription(locale,webservice, propertyName);
//			}
//			cache.put(key, missed);
//		} else {
		if (debug) {
			logger.debug("All caches missed for key: "+key);
		}
		String missed = null;
		missed = retrieveDescription(locale,username,webservice,propertyName);
		if(missed!=null) {
			if (debug) {
				logger.debug("Retrieve Level1 succeeded. Value: "+missed);
			}
		
			cache.put(key, missed);
			return missed;
		}
		if (debug) {
			logger.debug("Retrieve Level1 failed. Putting an anticache."+key);
		}
		cache.put(key, "%");
		
		missed = retrieveDescriptionWithUsernameWithoutService(locale,username,propertyName);
		key = "|"+locale+"|"+username+"|"+propertyName;
		if(missed!=null) {
			cache.put(key, missed);
			return missed;
		}
		if (debug) {
			logger.debug("Retrieve Level1b failed. Putting an anticache."+key);
		}
		cache.put(key, "%");

		
		missed = retrieveDescription(locale,webservice,propertyName);
		key = locale+"|"+webservice+"|" +propertyName;
		if(missed!=null) {
			cache.put(key, missed);
			return missed;
		}
		if (debug) {
			logger.debug("Retrieve Level2b failed. Putting an anticache."+key);
		}
		cache.put(key, "%");

		missed = retrieveDescription(locale,propertyName);
		key = locale+"|"+propertyName;
		if(missed!=null) {
			cache.put(key, missed);
			return missed;
		}
		if (debug) {
			logger.debug("Retrieve Level3 failed. Putting an anticache."+key);
		}
		cache.put(key, "%");
		if (debug) {
			logger.debug("No description present."+key);
		}
		
		return missed;
	}


	private String getDescriptionUser(String locale, String username, String webservice, String propertyName) {
		String key = "|"+locale+"|"+username+"|"+propertyName;
		if (debug) {
			logger.debug("Getting level2b: "+key);
		}
		String value = cache.get(key);
		if (value!=null) {
			if (!"%".equals(value)) {
				return value;
			} else {
				// anti-cache found, continue level2
				return getDescription(locale,webservice, propertyName);
//				return getDescription(locale,propertyName);

			}
		}
		
		return getDescription(locale,webservice, propertyName);
	}


	protected String getDescription(String locale, String webservice, String propertyName){
		String key = locale+"|"+webservice+"|"+propertyName;
		if (debug) {
			logger.debug("Getting level2: "+key);
		}
		String value =cache.get(key);
		if (value!=null) {
			if (!"%".equals(value)) {
				if (debug) {
					logger.debug("Cache hit level2: "+key+" : "+value);
				}
				return value;
			} else {
				// anti-cache found, continue level3
				if (debug) {
					logger.debug("Anti cache hit level2: "+key+" heading for level 3");
				}
				return getDescription(locale,propertyName);

			}
		}
		return null;
//		// cache miss, level2
//		String missed = retrieveDescription(locale,webservice,propertyName);
//		if (missed==null) {
//			// add an anti-cache
//			cache.put(key, "%");
//			return getDescription(locale,propertyName);
//		}
//		cache.put(key, missed);
//		return missed;
		}


	protected String getDescription(String locale, String propertyName){
		String key = locale+"|"+propertyName;
		if (debug) {
			logger.debug("Getting level3: "+key);
		}
		String value =cache.get(key);
		if ("%".equals(value)) {
			if (debug) {
				logger.debug("Level3 anticache hit: Nothing to retrieve "+key);
			}
			return null;
		}
		if (value!=null) {
			if (debug) {
				logger.debug("Level3 cache hit: "+value);
			}
			return value;
		}
		return null;
//		String desc =  retrieveDescription(locale,propertyName);
//		if (desc==null) {
//			cache.put(key, "%");
//			return null;			
//		}
//		cache.put(key, desc);
//		return desc;
		
	}

	
	public void flushUserCache(String user) {
//		for (Iterator<String> iter = cache.keySet().iterator(); iter.hasNext();) {
//			String element = (String) iter.next();
//			
//		}
	}
	

	protected abstract String retrieveDescription(String locale, String username, String webservice, String propertyName);
	protected abstract String retrieveDescription(String locale, String webservice, String propertyName);
	protected abstract String retrieveDescription(String locale, String propertyName);
	protected abstract String retrieveDescriptionWithUsernameWithoutService(String locale, String username, String propertyName);
	

	public void flushCache() {
		cache.clear();
		
	}

	public int getCacheSize() {
		return cache.size();
	}

}
