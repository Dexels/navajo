package com.dexels.navajo.server.enterprise.tribe;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.sharedstore.SerializationUtil;

/**
 * Wraps a Navajo object in a nice rug for cross Tribal transportation purposes

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
    private final long created;
    
    /**
     * interestCount can be used to indicate how many 'clients' will read the
     * Navajo enclosed in this Wrap.
     */
    private int interestCount = -1;

    /**
     * Start the collector for cleaning up persisted Navajo objects.
     */

    protected final String reference;

    private static final Logger logger = LoggerFactory.getLogger(DefaultNavajoWrap.class);
    private static final Random rand = new Random(System.currentTimeMillis());
    
    /*
     * Return a unique id for this wrap. If TribalNumber are available use those, if not use random long.
     */
    private final synchronized long getWrapCounter() {
    	if ( TribeManagerFactory.getInstance() != null ) {
    		TribalNumber tribalNumber =  TribeManagerFactory.getInstance().getDistributedCounter("DefaultNavajoWrap");
    		return tribalNumber.incrementAndGet();
    	} else {
    		return rand.nextLong();
    	}
    }
    
    /**
     * Create a DefaultNavajoWrap with auto id and unknown interestCount.
     * Interest will be derived from number of readObject (deserialization)
     * requests.
     * 
     * 
     * @param n
     */
    public DefaultNavajoWrap(Navajo n) {
        if (n == null) {
            logger.error("Cannot wrap null Navajo");
        }
        created = System.currentTimeMillis();
        reference = SerializationUtil.serializeNavajo(n, created + "-" + getWrapCounter() + ".xml");
    }

    /**
     * Create a DefaultNavajoWrap with auto id a specific interestCount.
     * 
     * @param n
     */
    public DefaultNavajoWrap(Navajo n, int interestCount) {
    	this.interestCount = interestCount;
        if (n == null) {
            logger.error("Cannot wrap null Navajo");
        }
        created = System.currentTimeMillis();
        reference = SerializationUtil.serializeNavajo(n, created + "-" + getWrapCounter() + ".xml");
    }

    /**
     * Create a DefaultNavajoWrap with a given id and unknown interestCount.
     * 
     * @param n
     * @param tribeManager
     */
    public DefaultNavajoWrap(Navajo n, String id) {
    	if (n == null) {
            logger.error("Cannot wrap null Navajo: " + id);
        }
        if (!id.endsWith(".xml")) {
            id = id + ".xml";
        }
        created = System.currentTimeMillis();
        reference = SerializationUtil.serializeNavajo(n, id + ".xml");
    }

    public long getCreated() {
        return created;
    }

    @Override
    public Navajo getNavajo() {
        if (myNavajo == null) {
            myNavajo = SerializationUtil.deserializeNavajo(reference);
            if (myNavajo == null) {
                logger.error(this + ": Could not de-serialize Navajo object: " + reference);
            }
        }
        return myNavajo;
    }

    /**
     * NOTE: MAKE SURE THAT WRITE/READ STREAM IS NOT BLOCKED BY THREAD DOING
     * HEAVY COMPUTATIONS! READ/WRITE SHOULD BE DONE QUICKLY ELSE THE PERSISTED
     * NAVAJO OBJECT WILL BE COLLECTED DUE TO 0 REFERENCE COUNT!
     * 
     */
    private void readObject(ObjectInputStream aStream) throws IOException, ClassNotFoundException {
        aStream.defaultReadObject();
        if (interestCount < 0) {
            if (WrapCollectorFactory.getInstance() != null) {
                WrapCollectorFactory.getInstance().updateReferenceCount(true, this);
            }
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        // If interestCount is set, use interestCount (number of clients) and
        // writer as total reference count.
        if (WrapCollectorFactory.getInstance() != null) {
            if (interestCount > 0) {
                WrapCollectorFactory.getInstance().updateReferenceCount(interestCount + 1, this);
            } else {
                WrapCollectorFactory.getInstance().updateReferenceCount(true, this);
            }
        }

    }

    @Override
    public void finalize() {
        try {
            // decrease reference count if object is garbage collected.
            if (WrapCollectorFactory.getInstance() != null) {
                WrapCollectorFactory.getInstance().updateReferenceCount(false, this);
            }
        } catch (Throwable t) {
        }
    }

}
