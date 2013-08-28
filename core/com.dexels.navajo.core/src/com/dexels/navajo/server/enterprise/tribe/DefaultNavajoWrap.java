package com.dexels.navajo.server.enterprise.tribe;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
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
