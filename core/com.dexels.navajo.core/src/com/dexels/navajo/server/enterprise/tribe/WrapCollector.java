package com.dexels.navajo.server.enterprise.tribe;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.sharedstore.SerializationUtil;

public class WrapCollector extends GenericThread {

    private static final int WORKER_SLEEP =  10000;
    private static final int CHECK_AFTER_AGE = 10000;
    private static final int TOO_OLD = 24 * 60 * 60 * 1000; // 24 hours is too
                                                            // old, remove it.

    // Use Cluster wide Map to store reference count.
    private ConcurrentMap<String, Wrapper> referenceCount;
    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    private TribeManagerInterface tribeManager;

    private static final Logger logger = LoggerFactory.getLogger(WrapCollector.class);

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void activate() {
        threadId = "Navajo Wrap Collector";
        WrapCollectorFactory.setInstance(this);
        logger.info("Activating WrapCollector");
        referenceCount = (ConcurrentMap) tribeManager.getDistributedMap("NavajoWrapReferenceCount");
        
        setSleepTime(WORKER_SLEEP);
        startThread(this);

    }

    public void deactivate() {
        logger.info("Deactiving WrapCollector");
        WrapCollectorFactory.setInstance(null);
        kill();
    }

    public void setTribeManager(TribeManagerInterface tribeManager) {
        this.tribeManager = tribeManager;
    }

    public void clearTribeManager(TribeManagerInterface tribeManager) {
        this.tribeManager = null;
    }

    protected void updateReferenceCount(final boolean increase, final DefaultNavajoWrap wrap) {
        executor.submit(new ReferenceCounter(referenceCount, wrap, increase));
    }

    protected void updateReferenceCount(final int total, final DefaultNavajoWrap wrap) {
        executor.submit(new ReferenceCounter(referenceCount, wrap, total));
    }

    @Override
    public synchronized void worker() {
        if (tribeManager == null || !tribeManager.getIsChief()) {
            return;
        }
        
        long currentTime = System.currentTimeMillis();
        try {
            for (String reference : referenceCount.keySet()) {
                try {
                    Wrapper key = referenceCount.get(reference);

                    long age = (currentTime - key.getLastUse());
                    if (age > CHECK_AFTER_AGE) {
                        if (key.getCount() == 0 || age > TOO_OLD) {
                            try {
                                logger.debug("Removing " + key.getReference());
                                SerializationUtil.removeNavajo(key.getReference());
                            } finally {
                                referenceCount.remove(reference);
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("Error on removing Wrapped object: " , e);
                }
            }
        } catch (Throwable e) {
            logger.error("Error in retrieving keys from referenceCount map: ", e);
        }

    }

}
