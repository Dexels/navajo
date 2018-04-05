package com.dexels.navajo.tipi.components.core;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TipiReentrantLockManager {

    private static TipiReentrantLockManager instance = null;
    private HashMap<String, ReentrantLock> lockmap = new HashMap<String, ReentrantLock>();;
    private static final Logger logger = LoggerFactory.getLogger(TipiReentrantLockManager.class);

    public static synchronized TipiReentrantLockManager getInstance() {
        if (instance == null) {
            instance = new TipiReentrantLockManager();
        }
        return instance;
    }

    public synchronized ReentrantLock getLock(String lockName) {
        if (!lockmap.containsKey(lockName)) {
            logger.debug("Creating lock : " + lockName);
            lockmap.put(lockName, new ReentrantLock());
        }
        logger.debug("Getting lock : " + lockName);
        return lockmap.get(lockName);
    }

    public synchronized void releaseLock(String lockName, ReentrantLock lock) {
        logger.debug("Releasing lock : " + lockName);
        lock.unlock();
        lockmap.remove(lockName);
    }

}
