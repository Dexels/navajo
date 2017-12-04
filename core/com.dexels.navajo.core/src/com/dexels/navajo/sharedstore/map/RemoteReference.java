package com.dexels.navajo.sharedstore.map;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.enterprise.tribe.TribeManagerFactory;
import com.dexels.navajo.util.Util;

/**
 * A RemoteReference object can be use in conjuction with the SharedTribalMap to store RemoteReference values.
 * A RemoteReference either holds an object or a reference to an object telling on which tribe member the
 * object is actually located. The current implementation of RemoteReference will by default store a remotely referenced object
 * on the calling tribe member once the getObject() method is used, hence RemoteReference can be viewed as a lazy
 * method of getting objects from the SharedTribalMap. However, if the RemoteReference is instantiated as RemoteReference(Object, true)
 * the Object is not encapsulated remotely. 
 * 
 * NOTE ON GARBAGE COLLECTION: 
 * RemoteReference objects SHOULD be explicitly removed from the SharedTribalMap if they need to be
 * garbage collected.
 * 
 * @author arjen
 *
 */
public class RemoteReference implements Serializable {

	/**
	 * 
	 */
	
	
	
	private final static Logger logger = LoggerFactory.getLogger(RemoteReference.class);

	
	private static final long serialVersionUID = -1993331465315897328L;
	
	private Object myObject;
	private String guid;
	private Object host;
	private boolean keepRemote = false;
	private volatile static Map<String,Object> myObjectRegistry = new HashMap<String,Object>();
	
	/**
	 * The 'remote' constructor.
	 * Creates a RemoteReference object for use by 'remote'. The encapsulated object is nullified and only
	 * the host and the ref value are passed.
	 * 
	 * @param r
	 * @return
	 */
	protected static RemoteReference createRemoteReference(RemoteReference r) {
		return new RemoteReference(r.getRef(), r.getHost(), r.isKeepRemote());
	}
	
	private RemoteReference(String ref, Object host, boolean keepRemote) {
		this.guid = ref;
		this.host = host;
		this.keepRemote = keepRemote;
	}
	
	/**
	 * The public constructor, used on the original parent of the object.
	 * 
	 * @param o
	 */
	public RemoteReference(Object o) {
		myObject = o;
		guid = Util.getRandomGuid();
		myObjectRegistry.put(guid, o );
		host = TribeManagerFactory.getInstance().getMyMembership().getAddress();
	}
	
	/**
	 * Another public constructor that can be used to specify that an encapsulated object should
	 * only be stored on the original parent. Remote peers only hold references to this object.
	 * 
	 * @param o
	 * @param keepRemote
	 */
	public RemoteReference(Object o, boolean keepRemote) {
		this(o);
		this.keepRemote = keepRemote;
	}

	/**
	 * Retrieves on object from the parent's object registry. If the tribe member does not
	 * hold the object null is returned.
	 * 
	 * @param ref
	 * @return
	 */
	public static Object getObject(String ref) {
		return myObjectRegistry.get(ref);
	}
	
	/**
	 * Gets the object. If the object is not yet present on the tribe member, it is retrieved from the parent.
	 * 
	 * @return
	 */
	public Object getObject() {
		if ( myObject != null ) {
			return myObject;
		} else {
			GetRemoteObjectAnswer a = (GetRemoteObjectAnswer) TribeManagerFactory.getInstance().askSomebody(new GetRemoteObjectRequest(this), host);
			if ( !keepRemote ) {
				myObject = a.getObject(); 
				return myObject;
			} else {
				return a.getObject();
			}
			
		}
	}

	/**
	 * Gets a unique reference string that is used to identify the encapsulated object.
	 * 
	 * @return
	 */
	public String getRef() {
		return guid;
	}

	/**
	 * Gets the address of the primary tribe member that holds the encapsulated object.
	 * 
	 * @return
	 */
	public Object getHost() {
		return host;
	}
	
	/**
	 * Make sure that object registry gets cleaned when the a RemoteReference object that holds the encapsulated
	 * object is garbage collected.
	 * 
	 */
	@Override
	public void finalize() {
		if ( myObject != null) {
			myObjectRegistry.remove(guid);
			logger.info("finalize() of RemoteReference " + guid + " callled, registry size: " + myObjectRegistry.size());
		}
	}

	public boolean isKeepRemote() {
		return keepRemote;
	}
}
