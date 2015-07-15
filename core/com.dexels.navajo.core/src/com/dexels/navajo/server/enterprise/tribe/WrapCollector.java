package com.dexels.navajo.server.enterprise.tribe;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.sharedstore.SerializationUtil;
import com.dexels.navajo.version.Version;

public class WrapCollector extends GenericThread {

	private final static int MAX_AGE = 10000;
	private final static int TOO_OLD = 24 * 60 * 60 * 1000; // 24 hours is too old, remove it.
	
	// Use Cluster wide Map to store reference count.
	private ConcurrentMap<String,Wrapper> referenceCount;
	private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
	private TribeManagerInterface tribeManager;
	
	private final static Logger logger = LoggerFactory.getLogger(WrapCollector.class);
	
	public WrapCollector() {
		if(!Version.osgiActive()) {
			setTribeManager(TribeManagerFactory.getInstance());
			activate();
		}
	}
		
	
	public void activate() {
		threadId = "Navajo Wrap Collector";
		if (tribeManager != null ) {
			logger.info("Using Tribal NavajoWrap Reference Count Map");
			referenceCount = (ConcurrentMap) tribeManager.getDistributedMap("NavajoWrapReferenceCount");
			//referenceCount = new ConcurrentHashMap<String,Wrapper>();
		} else {
			logger.warn("Using non-tribal referencecount map");
			referenceCount = new ConcurrentHashMap<String,Wrapper>();
		}
	}
	
	public void deactivate() {
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

		Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

		try {
			for ( String reference : referenceCount.keySet() ) {
				try {
					Wrapper key = referenceCount.get(reference);

					long age = ( System.currentTimeMillis() - key.getLastUse() );
					if ( age > MAX_AGE ) {
						Integer count = key.getCount();
						if ( count == null || count.intValue() == 0 || age > TOO_OLD ) {
							try {
								SerializationUtil.removeNavajo(key.getReference());
								logger.debug("Removing " + key.getReference());
							} finally {
								referenceCount.remove(reference);
							}
						}
					}
				} catch (Throwable t) {
					logger.error(t.getMessage(), t);
				}
			}
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}

	}
	
}
