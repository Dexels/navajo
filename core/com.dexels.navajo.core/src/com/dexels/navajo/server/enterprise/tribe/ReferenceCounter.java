package com.dexels.navajo.server.enterprise.tribe;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReferenceCounter implements Runnable {

	private final ConcurrentMap<String, Wrapper> referenceCount;
	private final DefaultNavajoWrap wrap;
	private final boolean increase;
	private int count = -1;
	
	private final static Logger logger = LoggerFactory.getLogger(ReferenceCounter.class);
	
	public ReferenceCounter(ConcurrentMap<String, Wrapper> referenceMap, DefaultNavajoWrap wrap, boolean increase) {
		this.referenceCount = referenceMap;
		this.wrap = wrap;
		this.increase = increase;
	}
	
	public ReferenceCounter(ConcurrentMap<String, Wrapper> referenceMap, DefaultNavajoWrap wrap, int count) {
		this.referenceCount = referenceMap;
		this.wrap = wrap;
		this.count = count;
		this.increase = true;
	}
	
	@Override
	public void run() {

		ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
		final Lock l = TribeManagerFactory.getInstance().getLock("DefaultNavajoWrapReferenceCounter");
		l.lock();

		try {

			Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

			Wrapper w = null;
			
			try {
				w = referenceCount.get(wrap.reference);
			} catch (Throwable t ) {
				logger.warn(t.getMessage(), t);
			}
			
			if ( w == null && increase ) {
				w = new Wrapper(wrap.reference, wrap.getCreated(), count);
			} else if ( w != null ) {
				if ( increase ) {
					if ( count > 0 ) {
						System.err.println("increasecount with " + count);
						w.increaseReference(count);
					} else {
						System.err.println("increase count.");
						w.increaseReference();
					}
				} else {
					w.decreaseReference();
				}
			} else {
				w = new Wrapper(wrap.reference, wrap.getCreated());
				w.resetCount();
			}
			System.err.println(wrap.reference + " has count " + w.getCount() );
			referenceCount.put(wrap.reference, w);
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		} finally {
			Thread.currentThread().setContextClassLoader(originalClassLoader);
			l.unlock();
		}
	}
	
}
