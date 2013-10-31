package com.dexels.navajo.persistence.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.NavajoListener;
import com.dexels.navajo.events.types.CacheExpiryEvent;
import com.dexels.navajo.events.types.NavajoCompileScriptEvent;
import com.dexels.navajo.persistence.Constructor;
import com.dexels.navajo.persistence.Persistable;
import com.dexels.navajo.persistence.PersistenceManager;
import com.dexels.navajo.server.CacheController;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.sharedstore.SharedStoreFactory;
import com.dexels.navajo.sharedstore.SharedStoreInterface;
import com.dexels.navajo.sharedstore.map.SharedTribalMap;
import com.dexels.navajo.util.AuditLog;



class Frequency implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 459349380931775302L;

	public Frequency(String name) {
		this.name = name;
		this.frequency = 0;
		this.creationDate = System.currentTimeMillis();
	}

	public boolean isExpired(long interval) {
		return ((creationDate + interval) < System.currentTimeMillis());
	}

	public void setCreation() {
		this.creationDate = System.currentTimeMillis();
		
	}

	public String getName() {
		return this.name;
	}

	public int getTimesAccessed() {
		return this.frequency;
	}

	private long creationDate;
	private String name;
	private int frequency = 0;
	
}

public final class PersistenceManagerImpl implements PersistenceManager, NavajoListener {

	
	private final static Logger logger = LoggerFactory
			.getLogger(PersistenceManagerImpl.class);
	
	/**
	 * Public bean properties
	 */
	
	public String key = "";
	public String serviceKeyValues = null;
	public boolean doClear;
	public double hitratio;
	
	/**
	 * Private parts.
	 */
    private long totalhits = 0;
    private long cachehits = 0;
    
    private volatile SharedTribalMap<String,PersistentEntry> inMemoryCache = null;
    private volatile SharedTribalMap<String,Frequency> accessFrequency = null;
	private volatile SharedStoreInterface sharedPersistenceStore = null;
	
	private static final Object semaphore = new Object();
	
	private static final String CACHE_PATH = "navajocache";
	private static final String MEMORY_CACHE_ID = "inMemoryCache";
	private static final String FREQUENCE_MAP_ID = "accessFrequency";
	
	public PersistenceManagerImpl() throws InstantiationException {
		try {
			Class.forName("com.dexels.navajo.sharedstore.map.SharedTribalMap");
		} catch (ClassNotFoundException e) {
			throw new InstantiationException(e.getMessage());
		}
	}
	
	public void init() {
		if ( this.sharedPersistenceStore == null ) {
			synchronized ( semaphore ) {
				if ( this.sharedPersistenceStore == null ) {
					sharedPersistenceStore = SharedStoreFactory.getInstance();
					if(sharedPersistenceStore==null) {
						// now what?
						// just ignore?
						return;
					}
					if ( TribeManagerFactory.getInstance().getIsChief() ) {
						sharedPersistenceStore.removeAll(CACHE_PATH); // Remove all cached entries when restarted.
					}
					inMemoryCache = new SharedTribalMap<String,PersistentEntry>(MEMORY_CACHE_ID);
					accessFrequency = new SharedTribalMap<String,Frequency>(FREQUENCE_MAP_ID);
					inMemoryCache = SharedTribalMap.registerMap(inMemoryCache, false);
					accessFrequency = SharedTribalMap.registerMap(accessFrequency, false);
					logger.info("============================================================================");
					logger.info("inMemoryCache = " + inMemoryCache);
					logger.info("accessFrequency = " + accessFrequency);
					logger.info("sharedPersistenceStore = " + sharedPersistenceStore);
					logger.info("============================================================================");
					// Register myself to the NavajoCompileScriptEvent in order to detect script recompiles and removed
					// cached scripts accordingly.
					NavajoEventRegistry.getInstance().addListener(NavajoCompileScriptEvent.class, this);
				}
			}
		}
	}
    

    @Override
	public final Persistable get(Constructor c, String key, String service, long expirationInterval, boolean persist) throws Exception {

    	init();

    	Persistable result = null;

    	if ( persist ) {
    		totalhits++;
    		synchronized (semaphore) {
    			result = read(key, service, expirationInterval);
    		}
    	}

    	if (result == null) {
    		result = c.construct();
    		if (persist) {
    			synchronized (semaphore) {
    				write(result, key, service);
    			}
    		}
    	} else if ( persist ){
    		cachehits++;
    	}

    	return result;
    }

    private final String constructServiceKeyValues(String serviceKeys, Navajo in) throws Exception {
    	
    	if ( serviceKeys == null ) {
    		return "";
    	}
    	
    	String [] properties = serviceKeys.split(",");
    	StringBuffer result = new StringBuffer();
    	
    	for (int i = 0; i < properties.length; i++) {
    		Property p = in.getProperty(properties[i]);
    		if ( p != null ) {
    			result.append(p.getValue());
    		}
    	}
    	
    	return result.toString();
    }
    
    private final Persistable memoryOperation(String key, String service, Persistable document, boolean read ) throws Exception {

    	if (read) {
//    		SoftReference<PersistentEntry> pc = null;
//    		Frequency freq = (Frequency) accessFrequency.get(key);
//    		if (freq != null && !freq.isExpired(expirationInterval)) {
//    			pc = (SoftReference<PersistentEntry>) inMemoryCache.get(key);
//    			if ( pc != null && pc.get() != null ) {
//    				return pc.get().getDocument();
//    			} else if ( pc != null ){
//    				inMemoryCache.remove(key);
//    			}
//    		} else if (freq != null && freq.isExpired(expirationInterval)) { 
//    			SoftReference<PersistentEntry> rr = (SoftReference<PersistentEntry>) inMemoryCache.get(freq.getName());
//    			if ( rr != null && rr.get() != null ) {
//    				inMemoryCache.remove(freq.getName());
//    			}
//    		}
    		return null;
    	} else { // WRITE (ONLY WRITE METADATA TO MEMORY NOT ENTIRE NAVAJO!)
    		if (inMemoryCache.get(key) == null) {

    			Frequency freq = (Frequency) accessFrequency.get(key);
    			if (freq != null) {
    				freq.setCreation();
    			} else {
    				freq = new Frequency(key);
    				accessFrequency.put(key, freq);
    			}
    			PersistentEntry pe = new PersistentEntry(service);
    			String keys = CacheController.getInstance().getServiceKeys( service );
    			pe.setKeyValues( constructServiceKeyValues(keys, (Navajo) document ) );
    			inMemoryCache.put(key, pe );

    		}
    		return document;
    	}

    }

    private String getServicePath(String key) {

    	int lastIndex = key.lastIndexOf(".");
    	if ( lastIndex != -1 ) {
    		String path = key.substring( 0, lastIndex );
    		return path.replaceAll("\\.", "/");
    	} else {
    		return "";
    	}
    }

    /**
     * Note that write() is a critical section since multiple requests using the same key can be expected!
     */
    public final boolean write(Persistable document, String key, String service) {

    	OutputStream os = null;
    	try {
        	memoryOperation(key, service, document, false);
            
        	os = sharedPersistenceStore.getOutputStream(CACHE_PATH + "/" + getServicePath(key), key, false);
        	((Navajo) document).write(os);
            return true;
        } catch (Exception e) {
        	logger.error("Error: ", e);
        	return false;
        } finally {
        	if ( os != null ) {
				try {
				os.close();
				} catch (Exception e) {}
			}
        }
    }

    private final boolean isExpired(long stamp, long interval) {
        long now = System.currentTimeMillis();
        if ((stamp + interval) <= now)
            return true;
        else
            return false;
    }

    public final Persistable read(String key, String service, long expirationInterval) {
        Navajo pc = null;

        try {
          
        	String path = CACHE_PATH + "/" + getServicePath(key);
        	
            if ( sharedPersistenceStore.exists(path, key) ) {
            	
                if (isExpired(sharedPersistenceStore.lastModified(path, key), expirationInterval)) {
                	sharedPersistenceStore.remove(path, key);
                	NavajoEventRegistry.getInstance().publishEvent(new CacheExpiryEvent(service, key));
                    return null;
                }
                
                InputStream is = sharedPersistenceStore.getStream(path, key);
                if ( is != null ) {
                	pc = NavajoFactory.getInstance().createNavajo(is);
                	is.close();
                }
                if (inMemoryCache.get(key) == null) {
                	memoryOperation(key, service, pc, false);
                }
                
            } else {
            	
                return null;
            }
        } catch (Exception e) {
        	logger.error("Error: ", e);
            return null;
        } 
        
        return pc;
    }

	@Override
	public void setConfiguration(Navajo config) {
		
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key.replace('/', '.');
	}


	public boolean isCached(String service, String serviceKeyValues) {
		PersistenceManagerImpl pm = (PersistenceManagerImpl) DispatcherFactory.getInstance().getNavajoConfig().getPersistenceManager();
		Iterator iter = pm.inMemoryCache.values().iterator();
		while ( iter.hasNext() ) {
			PersistentEntry pe = (PersistentEntry) iter.next();
			if ( pe != null && (  pe.getService().equals(service) && ( serviceKeyValues == null || pe.getKeyValues().equals(serviceKeyValues) ) ) ) {
				return true;
			}
		}
		return false;
	}
	
	private void removeCachedObjects(PersistenceManagerImpl pm, String parent, String myKey) {
		String [] all = pm.sharedPersistenceStore.getObjects(parent);
		for ( int i = 0; i < all.length; i++ ) {
			if ( all[i].startsWith(myKey) ) {
				pm.sharedPersistenceStore.remove(parent, all[i]);
			}
		}	
		// Recurse into parents.
		String [] allParents = pm.sharedPersistenceStore.getParentObjects(parent);
		for ( int i = 0; i < allParents.length; i++ ) {
			removeCachedObjects(pm, parent + "/" + allParents[i], myKey);
		}
	}
	
	public void setDoClear(boolean doClear) {
		PersistenceManagerImpl pm = (PersistenceManagerImpl) DispatcherFactory.getInstance().getNavajoConfig().getPersistenceManager();

		if ( doClear && pm.inMemoryCache != null && pm.sharedPersistenceStore != null ) {
			Set<String> keys = new HashSet<String>( pm.inMemoryCache.keySet() );
			Iterator<String> iter = keys.iterator();
			while ( iter.hasNext() ) {
				String cacheKey = iter.next();
	        	String path = CACHE_PATH + "/" + getServicePath(cacheKey);
	        	
				if ( cacheKey.startsWith(key ) && this.serviceKeyValues == null ) {
					pm.inMemoryCache.remove(cacheKey);
					pm.sharedPersistenceStore.remove(path, cacheKey);
				} else if ( cacheKey.startsWith(key ) && this.serviceKeyValues != null ) {
					PersistentEntry pe = (PersistentEntry) pm.inMemoryCache.get(cacheKey);
					if ( pe != null && pe.getKeyValues().equals(serviceKeyValues) ) {
						pm.inMemoryCache.remove(cacheKey);
						pm.sharedPersistenceStore.remove(path, cacheKey);
					}
				}
			}
			// Remove all persisted cache objects that start with the given cache key to prevent 
			// loading of invalidated cache entries. This is rather brute force since in the current
			// implementation we do not store PersistentEntry objects but entire Navajo documents, hence
			// we can not check the serviceKeyValues....
			
			removeCachedObjects(pm, CACHE_PATH, key);
			
		}
	}

	@Override
	public double getHitratio() {
		PersistenceManagerImpl pm = (PersistenceManagerImpl) DispatcherFactory.getInstance().getNavajoConfig().getPersistenceManager();
		return ( (double) pm.cachehits / (double) pm.totalhits );
	}


	public void setServiceKeyValues(String serviceKeyValues) {
		this.serviceKeyValues = serviceKeyValues;
	}
	
	public static void main ( String [] args ) throws Exception {
		
		String service = "vla.competition.ProcessGetCompetitionWeek";
		PersistenceManagerImpl pi = new PersistenceManagerImpl();
		
		System.err.println(pi.getServicePath(service));
		
	}

	@Override
	public Persistable get(Constructor c, String key, long expirationInterval,
			boolean persist) throws Exception {
		throw new IllegalStateException("Persistable implentation error");
	}

	@Override
	public void clearCache() {
		setDoClear(true);
	}

	@Override
	public void onNavajoEvent(NavajoEvent ne) {
		
		if ( ne instanceof NavajoCompileScriptEvent ) {
			NavajoCompileScriptEvent ncse = (NavajoCompileScriptEvent) ne;
			//AuditLog.log("PERSISTENCEMANAGER", "Received NavajoCompileScriptEvent for " + ncse.getWebservice(), Level.INFO);
			PersistenceManagerImpl p;
			try {
				p = new PersistenceManagerImpl();
				p.setKey(ncse.getWebservice());
				p.setDoClear(true);
			} catch (InstantiationException e) {
				AuditLog.log("PERSISTENCEMANAGER", e.getMessage(), Level.SEVERE);
			}
			
		}
	}
	
}
