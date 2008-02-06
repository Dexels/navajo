package com.dexels.navajo.persistence.impl;

import java.io.Serializable;
import java.lang.ref.SoftReference;

import com.dexels.navajo.document.*;
import com.dexels.navajo.persistence.*;
import com.dexels.navajo.tribe.SharedStoreFactory;
import com.dexels.navajo.tribe.SharedStoreInterface;
import com.dexels.navajo.tribe.map.SharedTribalMap;

class Configuration {
	protected String persistencePath = "navajocache";
	protected boolean statistics = false;
}

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

    public static Configuration configuration = new Configuration();

    private static long totalhits = 0;
    private static long cachehits = 0;
    private static long fileWrites = 0;
    
    private static SharedTribalMap<String,SoftReference<Persistable>> inMemoryCache = null;
    private static SharedTribalMap<String,Frequency> accessFrequency = null;

	private volatile SharedStoreInterface sharedPersistenceStore = null;
	
	private static final Object semaphore = new Object();
	
    private void init() {
    	if ( this.sharedPersistenceStore == null ) {
    		synchronized ( semaphore ) {
    			if ( this.sharedPersistenceStore == null ) {
    				sharedPersistenceStore = SharedStoreFactory.getInstance();
    				inMemoryCache = new SharedTribalMap<String, SoftReference<Persistable>>("inMemoryCache");
    				accessFrequency = new SharedTribalMap<String,Frequency>("accessFrequency");
    				inMemoryCache = SharedTribalMap.registerMap(inMemoryCache, false);
    				accessFrequency = SharedTribalMap.registerMap(accessFrequency, false);
    			}
    		}
    	}
    }
    
    private synchronized Configuration readConfiguration(Navajo config) {
    	try {

    		Configuration c = new Configuration();
    		c.persistencePath = "navajocache";
    		
    		return c;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }

    public final Persistable get(Constructor c, String key, long expirationInterval, boolean persist) throws Exception {

    	init();
    	
    	if (configuration == null) {
        	return c.construct();
        }
    	
        totalhits++;

        Persistable result = (persist) ? read(key, expirationInterval) : null;

        long start = (persist ? System.currentTimeMillis() : 0);

        if (result == null) {
            result = c.construct();
          
            if (persist) {
            	 write(result, key);
            }
        } else {
            cachehits++;
         }

        long end = (persist ? System.currentTimeMillis() : 0);

        return result;
    }

    private final synchronized Persistable memoryOperation(String key, Persistable document, long expirationInterval, boolean read ) {

    	if (read) {
    		SoftReference<Persistable> pc = null;
    		Frequency freq = (Frequency) accessFrequency.get(key);
    		if (freq != null && !freq.isExpired(expirationInterval)) {
    			pc = (SoftReference<Persistable>) inMemoryCache.get(key);
    			if (pc != null && pc.get() != null) {
    				System.err.println("Found Softreference, returning it... ");
    				return pc.get();
    			} else if ( pc != null ){
    				System.err.println("Softreference was gargage collected....");
    				inMemoryCache.remove(key);
    			}
    		}
    		if (freq != null && freq.isExpired(expirationInterval)) {
    			SoftReference<Persistable> d = (SoftReference<Persistable>) inMemoryCache.get(freq.getName());
    			if ( d.get() != null) {
    				inMemoryCache.remove(freq.getName());
    				System.err.println("REMOVING EXPIRED MEMORY CACHE ENTRY: " + key );
    			} else {
    				System.err.println("NOT REMOVING EXPIRED SOFTREFERENCE: " + key);
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
    			System.err.println("PUTTING " + key + " IN CACHE!!");
    			inMemoryCache.put(key, new SoftReference<Persistable>(document));

    		}
    		return document;
    	}

    }

    /**
     * Note that write() is a critical section since multiple requests using the same key can be expected!
     */
    public final synchronized boolean write(Persistable document, String key) {

        try {
        	memoryOperation(key, document, -1, false);
            
            sharedPersistenceStore.store(configuration.persistencePath, key, document, false, false);
            
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

    public final Persistable read(String key, long expirationInterval) {
        Navajo pc = null;

        pc = (Navajo) memoryOperation(key, null, expirationInterval, true );
        if (pc != null) {  // Found in memory cache.
          System.err.println("Returning FROM MEMORY cache: " + key);
          return pc;
        } else {
        	System.err.println("Did not find in MEMORY cache: " + key + ", trying persistent store....");
        }

       
        try {
          
            if ( sharedPersistenceStore.exists(configuration.persistencePath, key) ) {
            	
            	System.err.println("FOUND " + key + " IN PERSISTENT STORE...");
                if (isExpired(sharedPersistenceStore.lastModified(configuration.persistencePath, key), expirationInterval)) {
                	System.err.println("REMOVING EXPIRED FILE CACHE ENTRY: " + key);
                	sharedPersistenceStore.remove(configuration.persistencePath, key);
                    return null;
                }
                
                pc = (Navajo) sharedPersistenceStore.get(configuration.persistencePath, key);
                
                if (inMemoryCache.get(key) == null) {
                  memoryOperation(key, pc, expirationInterval, false);
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
		configuration = readConfiguration(config);
	}
}
