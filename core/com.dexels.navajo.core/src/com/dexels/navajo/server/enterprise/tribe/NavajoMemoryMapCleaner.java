package com.dexels.navajo.server.enterprise.tribe;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.GenericThread;

public class NavajoMemoryMapCleaner extends GenericThread { 
    private static final Logger logger = LoggerFactory.getLogger(NavajoMemoryMapCleaner.class);

    private static final int WORKER_SLEEP =  60*60*1000; // run once an hour

    private TribeManagerInterface tribeManager;
    
    public void activate() {
        threadId = "Navajo Memory Map Cleaner";
        logger.info("Activating NavajoMapCleaner");        
        setSleepTime(WORKER_SLEEP);
        startThread(this);

    }
    
    public void deactivate() {
        logger.info("Deactiving NavajoMapCleaner");
        kill();
    }
    
    public void setTribeManager(TribeManagerInterface tribeManager) {
        this.tribeManager = tribeManager;
    }

    public void clearTribeManager(TribeManagerInterface tribeManager) {
        this.tribeManager = null;
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public synchronized void worker() {
        if (!tribeManager.getIsChief()) {
            // Only run on chief
            return;
        }
        
        long currentTime = System.currentTimeMillis();
        Set<Object> toRemove = new HashSet<>();
        
        try {
            Map memoryMap = tribeManager.getDistributedMap(TribeManagerInterface.MEMORY_MAP_KEY);
            Map expMap = tribeManager.getDistributedMap(TribeManagerInterface.MEMORY_MAP_EXP_KEY);
            
            for (Object key : memoryMap.keySet()) {
                if (expMap.containsKey(key) ) {
                    Long expTime = (Long) expMap.get(key);
                    if (currentTime > expTime) {
                        toRemove.add(key);
                    }
                }
            }
            
            for (Object key : toRemove) {
                memoryMap.remove(key);
                expMap.remove(key);
            }
        } catch (Throwable e) {
            logger.error("Error in cleaning Navajo Memory Map: ", e);
        }

    }
    
}
