/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.sharedstore.map;

import java.io.Serializable;
import java.lang.ref.SoftReference;


/**
 * 
 * Defines an element for the SharedTribalMap.	
 * @author arjen
 *
 */
public class SharedTribalElement implements Serializable {

	
	private static final long serialVersionUID = -4266747466983097925L;

	private String id;
	private Object key;
	private Object value;
	// TODO: Use  originatingHost to complete 'handshake' protocol in order to make SharedTribalMap operations 'tribal safe'.
	private boolean hasSoftReference = false;
	
	public SharedTribalElement(String id, Object key, Object value) {
		this.id = id;
		this.key = key;
		if ( value instanceof SoftReference<?>) {
			hasSoftReference = true;
			this.value = ((SoftReference<?>) value).get();
		} else {
			hasSoftReference = false;
			this.value = value;
		}
		
		if ( value instanceof RemoteReference ) {
			this.value = RemoteReference.createRemoteReference( (RemoteReference) value);
		}
	}

	/**
	 * The unique id of the tribal map.
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * The key to the map.
	 * 
	 * @return
	 */
	public Object getKey() {
		return key;
	}

	/**
	 * The value of the key.
	 * 
	 * @return
	 */
	public Object getValue() {
		if ( !hasSoftReference ) {
			return value; 
		} else {
			return new SoftReference<Object>(value);
		}
	}

	public boolean isHasSoftReference() {
		return hasSoftReference;
	}

}
