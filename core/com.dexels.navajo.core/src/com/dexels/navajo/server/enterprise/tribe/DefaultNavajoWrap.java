package com.dexels.navajo.server.enterprise.tribe;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.sharedstore.SerializationUtil;

/**
 * Wraps a Navajo object in a nice rug for cross Tribal transportation purposes
 * 
 * @TODO: Register DefaultNavajoWrap object with Garbage Collector to remove 'old' objects.
 * 
 * @author arjenschoneveld
 * 
 *
 */

class Wrapper implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1776008626104972375L;
	private final String reference;
	private final long created;
	public int count;
	
	public Wrapper(String reference, long created) {
		this.reference = reference;
		this.created = created;
		count = 1;
	}

	public int getCount() {
		return count;
	}

	public String getReference() {
		return reference;
	}
	
	public void resetCount() {
		count = 0;
	}
	
	public void increaseReference() {
		count++;
	}
	
	public void decreaseReference() {
		if ( count > 0 ) {
			count--;
		}
	}

	public long getCreated() {
		return created;
	}
	
}

class ReferenceCounter implements Runnable {

	private final ConcurrentMap<String, Wrapper> referenceCount;
	private final DefaultNavajoWrap wrap;
	private final boolean increase;
	
	public ReferenceCounter(ConcurrentMap<String, Wrapper> referenceMap, DefaultNavajoWrap wrap, boolean increase) {
		this.referenceCount = referenceMap;
		this.wrap = wrap;
		this.increase = increase;
	}
	
	@Override
	public void run() {

		ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
		final Lock l = TribeManagerFactory.getInstance().getLock("DefaultNavajoWrapReferenceCounter");
		l.lock();

		try {

			Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

			Wrapper w = referenceCount.get(wrap.reference);

			if ( w == null && increase ) {
				w = new Wrapper(wrap.reference, wrap.getCreated());
			} else if ( w != null ) {
				if ( increase ) {
					w.increaseReference();
				} else {
					w.decreaseReference();
				}
			} else {
				w = new Wrapper(wrap.reference, wrap.getCreated());
				w.resetCount();
			}
			referenceCount.put(wrap.reference, w);
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		} finally {
			Thread.currentThread().setContextClassLoader(originalClassLoader);
			l.unlock();
		}
	}
	
}

class WrapCollector extends GenericThread {

	private final static int MAX_AGE = 2000;

	// Use Cluster wide Map to store reference count.
	private final ConcurrentMap<String,Wrapper> referenceCount;
	private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
	
	private final static Logger logger = LoggerFactory.getLogger(WrapCollector.class);
	
	public WrapCollector() {
		threadId = "Navajo Wrap Collector";
		if ( TribeManagerFactory.getInstance() != null ) {
			logger.info("Using Tribal NavajoWrap Reference Count Map");
			referenceCount = (ConcurrentMap) TribeManagerFactory.getInstance().getDistributedMap("NavajoWrapReferenceCount");
			//referenceCount = new ConcurrentHashMap<String,Wrapper>();
		} else {
			logger.warn("Using non-tribal referencecount map");
			referenceCount = new ConcurrentHashMap<String,Wrapper>();
		}
		Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
	}
		
	protected void updateReferenceCount(final boolean increase, final DefaultNavajoWrap wrap) {
		executor.submit(new ReferenceCounter(referenceCount, wrap, increase));
	}

	@Override
	public synchronized void worker() {
		
		try {
			for ( String reference : referenceCount.keySet() ) {
				Wrapper key = referenceCount.get(reference);
				
				long age = ( System.currentTimeMillis() - key.getCreated() );
				if ( age > MAX_AGE ) {
					Integer count = key.getCount();
					if ( count == null || count.intValue() == 0 ) {
						try {
							SerializationUtil.removeNavajo(key.getReference());
							logger.info("Removing " + key.getReference());
						} finally {
							referenceCount.remove(reference);
						}
					}
				}
			}
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
	}
	
}

public class DefaultNavajoWrap implements NavajoRug {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4689405438923422437L;
	private transient Navajo myNavajo = null;
	private final static TribalNumber uniqueId = TribeManagerFactory.getInstance().getDistributedCounter("DefaultNavajoWrap");
	private final static WrapCollector wrapCollector;
	private final long created;
	
	static {
		wrapCollector = new WrapCollector();
		wrapCollector.setSleepTime(2000);
		wrapCollector.startThread(wrapCollector);
	}
	
	protected final String reference;
	
	private final static Logger logger = LoggerFactory.getLogger(DefaultNavajoWrap.class);
	
	public DefaultNavajoWrap(Navajo n) {
		if ( n == null ) {
			logger.error("Cannot wrap null Navajo");
		}
		created = System.currentTimeMillis();
		reference = SerializationUtil.serializeNavajo(n, created + "-" + uniqueId.incrementAndGet() + ".xml");
		wrapCollector.updateReferenceCount(true, this);
	}
	
	public DefaultNavajoWrap(Navajo n, String id) {
		if ( n == null ) {
			logger.error("Cannot wrap null Navajo: " + id);
		}
		if ( !id.endsWith(".xml") ) {
			id = id + ".xml";
		}
		created = System.currentTimeMillis();
		reference = SerializationUtil.serializeNavajo(n, id + ".xml");
		wrapCollector.updateReferenceCount(true, this);
	}
	
	public long getCreated() {
		return created;
	}
	
	public Navajo getNavajo() {
		if ( myNavajo == null ) {
			myNavajo = SerializationUtil.deserializeNavajo(reference);
			if ( myNavajo == null ) {
				logger.error(this + ": Could not de-serialize Navajo object: " + reference);
			}
		}
		return myNavajo;
	}
	
	private void readObject(ObjectInputStream aStream) throws IOException, ClassNotFoundException {
		aStream.defaultReadObject();
		wrapCollector.updateReferenceCount(true, this);
	}

	public void finalize() {
		try {
		wrapCollector.updateReferenceCount(false, this);
		} catch (Throwable t) {}
	}
	
}
