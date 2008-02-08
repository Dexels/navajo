package com.dexels.navajo.persistence.impl;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.dexels.navajo.document.*;
import com.dexels.navajo.persistence.*;
import com.dexels.navajo.server.CacheController;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.tribe.SharedStoreFactory;
import com.dexels.navajo.tribe.SharedStoreInterface;
import com.dexels.navajo.tribe.map.RemoteReference;
import com.dexels.navajo.tribe.map.SharedTribalMap;


/**
 * TODO use softreference listener to persist objects to disk...??
 * 
 * @author arjen
 *
 */
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

public final class PersistenceManagerImpl implements PersistenceManager {

	/**
	 * Public bean properties
	 */
	
	public String key;
	public String serviceKeyValues = null;
	public boolean doClear;
	public double hitratio;
	
	/**
	 * Private parts.
	 */
    private long totalhits = 0;
    private long cachehits = 0;
    private long fileWrites = 0;
    
    private volatile SharedTribalMap<String,?> inMemoryCache = null;
    private volatile SharedTribalMap<String,Frequency> accessFrequency = null;
	private volatile SharedStoreInterface sharedPersistenceStore = null;
	
	private static final Object semaphore = new Object();
	
	private static final String CACHE_PATH = "navajocache";
	private static final String MEMORY_CACHE_ID = "inMemoryCache";
	private static final String FREQUENCE_MAP_ID = "accessFrequency";
	
    private void init() {
    	if ( this.sharedPersistenceStore == null ) {
    		synchronized ( semaphore ) {
    			if ( this.sharedPersistenceStore == null ) {
    				sharedPersistenceStore = SharedStoreFactory.getInstance();
    				inMemoryCache = new SharedTribalMap(MEMORY_CACHE_ID);
    				accessFrequency = new SharedTribalMap<String,Frequency>(FREQUENCE_MAP_ID);
    				inMemoryCache = SharedTribalMap.registerMap(inMemoryCache, false);
    				accessFrequency = SharedTribalMap.registerMap(accessFrequency, false);
    			}
    		}
    	}
    }
    

    public final Persistable get(Constructor c, String key, String service, long expirationInterval, boolean persist) throws Exception {

    	init();

    	totalhits++;

    	Persistable result = null;

    	if ( persist ) {
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
    	} else {
    		cachehits++;
    	}

    	return result;
    }

    private final String constructServiceKeyValues(String serviceKeys, Navajo in) {
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
    
    private final Persistable memoryOperation(String key, String service, Persistable document, long expirationInterval, boolean read ) {

    	if (read) {
    		RemoteReference pc = null;
    		Frequency freq = (Frequency) accessFrequency.get(key);
    		if (freq != null && !freq.isExpired(expirationInterval)) {
    			pc = (RemoteReference) inMemoryCache.get(key);
    			if ( pc != null ) {
    				SoftReference sr = (SoftReference) pc.getObject();
    				if (sr != null &&  sr.get() != null) {
    					return ((PersistentEntry) sr.get()).getDocument();
    				} else if ( sr != null ){
    					inMemoryCache.remove(key);
    				}
    			}
    		}
    		if (freq != null && freq.isExpired(expirationInterval)) { 
    			RemoteReference rr = (RemoteReference) inMemoryCache.get(freq.getName());
    			if ( rr != null ) {
    				SoftReference d = (SoftReference) rr.getObject();
    				if ( d != null && d.get() != null) {
    					inMemoryCache.remove(freq.getName());
    					System.err.println("REMOVING EXPIRED MEMORY CACHE ENTRY: " + key );
    				} else {
    					System.err.println("NOT REMOVING EXPIRED SOFTREFERENCE: " + key);
    				}
    			}
    		}
    		return null;
    	} else { // WRITE
    		if (inMemoryCache.get(key) == null) {

    			Frequency freq = (Frequency) accessFrequency.get(key);
    			if (freq != null) {
    				freq.setCreation();
    			} else {
    				freq = new Frequency(key);
    				accessFrequency.put(key, freq);
    			}
    			PersistentEntry pe = new PersistentEntry(document, service);
    			String keys = CacheController.getInstance().getServiceKeys( service );
    			pe.setKeyValues( constructServiceKeyValues(keys, (Navajo) document ) );
    			inMemoryCache.put(key, new RemoteReference( new SoftReference( pe ) ) );

    		}
    		return document;
    	}

    }


    /**
     * Note that write() is a critical section since multiple requests using the same key can be expected!
     */
    public final boolean write(Persistable document, String key, String service) {

        try {
        	memoryOperation(key, service, document, -1, false);
            
            sharedPersistenceStore.store(CACHE_PATH, key, document, false, false);
            
            fileWrites++;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

        pc = (Navajo) memoryOperation(key, service, null, expirationInterval, true );
        if (pc != null) {  // Found in memory cache.
          System.err.println("Returning FROM MEMORY cache: " + key);
          return pc;
        } else {
        	System.err.println("Did not find in MEMORY cache: " + key + ", trying persistent store....");
        }

       
        try {
          
            if ( sharedPersistenceStore.exists(CACHE_PATH, key) ) {
            	
            	System.err.println("FOUND " + key + " IN PERSISTENT STORE...");
                if (isExpired(sharedPersistenceStore.lastModified(CACHE_PATH, key), expirationInterval)) {
                	System.err.println("REMOVING EXPIRED FILE CACHE ENTRY: " + key);
                	sharedPersistenceStore.remove(CACHE_PATH, key);
                    return null;
                }
                
                pc = (Navajo) sharedPersistenceStore.get(CACHE_PATH, key);
                
                if (inMemoryCache.get(key) == null) {
                  memoryOperation(key, service, pc, expirationInterval, false);
                }
                
            } else {
            	
            	System.err.println("DID NOT FIND " + key + " IN PERSISTENT STORE...");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } 
        
        return pc;
    }

	public void setConfiguration(Navajo config) {
		
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public boolean isCached(String service, String serviceKeyValues) {
		System.err.println("IN isCached (" + service + ", " + serviceKeyValues + ")");
		PersistenceManagerImpl pm = (PersistenceManagerImpl) Dispatcher.getInstance().getNavajoConfig().getPersistenceManager();
		Iterator iter = pm.inMemoryCache.values().iterator();
		while ( iter.hasNext() ) {
			RemoteReference re = (RemoteReference) iter.next();
			SoftReference se = (SoftReference) re.getObject();
			if ( se != null ) {
				PersistentEntry pe = (PersistentEntry) se.get();
				if ( pe != null && ( serviceKeyValues == null ||  pe.getService().equals(service) && pe.getKeyValues().equals(serviceKeyValues) ) ) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void setDoClear(boolean doClear) {
		PersistenceManagerImpl pm = (PersistenceManagerImpl) Dispatcher.getInstance().getNavajoConfig().getPersistenceManager();
		synchronized (semaphore) {
			if ( doClear && pm.inMemoryCache != null && pm.sharedPersistenceStore != null ) {
				Set keys = new HashSet( pm.inMemoryCache.keySet() );
				Iterator iter = keys.iterator();
				while ( iter.hasNext() ) {
					String cacheKey = (String) iter.next();
					if ( cacheKey.startsWith(key ) && this.serviceKeyValues == null ) {
						pm.inMemoryCache.remove(cacheKey);
						pm.sharedPersistenceStore.remove(CACHE_PATH, cacheKey);
					} else if ( cacheKey.startsWith(key ) && this.serviceKeyValues != null ) {
						RemoteReference re = (RemoteReference) pm.inMemoryCache.get(cacheKey);
						SoftReference se = (SoftReference) re.getObject();
						if ( se != null ) {
							PersistentEntry pe = (PersistentEntry) se.get();
							if ( pe != null ) {
								
								if ( pe.getKeyValues().equals(serviceKeyValues) ) {
									pm.inMemoryCache.remove(cacheKey);
									pm.sharedPersistenceStore.remove(CACHE_PATH, cacheKey);
								}
							}
						}
					}
				}
				// Remove all persisted cache objects that start with the given cache key to prevent 
				// loading of invalidated cache entries. This is rather brute force since in the current
				// implementation we do not store PersistentEntry objects but entire Navajo documents, hence
				// we can not check the serviceKeyValues....
				String [] all = pm.sharedPersistenceStore.getObjects(CACHE_PATH);
				for ( int i = 0; i < all.length; i++ ) {
					if ( all[i].startsWith(key) ) {
						pm.sharedPersistenceStore.remove(CACHE_PATH, all[i]);
					}
				}
			} 
		}
	}

	public double getHitratio() {
		PersistenceManagerImpl pm = (PersistenceManagerImpl) Dispatcher.getInstance().getNavajoConfig().getPersistenceManager();
		return ( (double) pm.cachehits / (double) pm.totalhits );
	}


	public void setServiceKeyValues(String serviceKeyValues) {
		this.serviceKeyValues = serviceKeyValues;
	}
	
	public static void main ( String [] args ) {
		String [] aap = "aap,noot".split(",");
		System.err.println(aap[1]);
	}
}
