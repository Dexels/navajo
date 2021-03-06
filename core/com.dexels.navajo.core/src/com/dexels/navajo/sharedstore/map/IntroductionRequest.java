/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.sharedstore.map;

import java.lang.ref.SoftReference;

import com.dexels.navajo.server.enterprise.tribe.Answer;
import com.dexels.navajo.server.enterprise.tribe.Request;

/**
 * An IntroductionRequest is used to inform a new member of active 'tribal maps'.
 * An IntroductionRequest is ALWAYS issued by the chief.
 * 
 * @author arjen
 *
 */
public class IntroductionRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3677395808956459975L;
	
	private SharedTribalMap<?,?> stm = null;
	private boolean hasSoftReferences = false;
	
	@SuppressWarnings("rawtypes")
	public IntroductionRequest(SharedTribalMap<?,?> stm) {
		
		boolean hasRemoteReferences = false;
		
		super.blocking = false;
		// Check for SoftReference values...
		java.util.Iterator<?> iter = stm.values().iterator();
		if ( iter.hasNext() ) {
			Object o = iter.next();
			if ( o instanceof SoftReference<?>) {
				hasSoftReferences = true;
			} else if ( o instanceof RemoteReference ) {
				hasRemoteReferences = true;
			}
		}
		if ( hasSoftReferences ) { // Handle soft references: get original value without soft reference encapsulation.
			SharedTribalMap<?,?> copy = new SharedTribalMap(stm.getId());
			iter = stm.keySet().iterator();
			while ( iter.hasNext() ) {
				Object key = iter.next();
				SoftReference<?> value = (SoftReference<?>) stm.get(key);
				if ( value != null ) {
					copy.putLocal(key, value.get());
				}
			}
			this.stm = copy;
		} else if ( hasRemoteReferences ) { // Handle remote references: get RemoteReference object for 'remote'.
			SharedTribalMap<?,?> copy = new SharedTribalMap(stm.getId());
			iter = stm.keySet().iterator();
			while ( iter.hasNext() ) {
				Object key = iter.next();
				RemoteReference value = RemoteReference.createRemoteReference( (RemoteReference) stm.get(key) );
				if ( value != null ) {
					copy.putLocal( key, value );
				}
			}
			this.stm = copy;
		}
		else {
			this.stm = stm;
		}
	}
	
	/**
	 * The getAnswer() is ALWAYS executed on the new member to register a tribal map.
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Answer getAnswer() {
		if ( !hasSoftReferences ) {
			SharedTribalMap.registerMapLocal(stm);
		} else {
			SharedTribalMap<?,?> newOriginal = new SharedTribalMap(stm.getId());
			java.util.Iterator<?> iter = stm.keySet().iterator();
			while ( iter.hasNext() ) {
				Object key = iter.next();
				Object value = stm.get(key);
				newOriginal.putLocal(key, new SoftReference<Object>(value));
			}
			SharedTribalMap.registerMapLocal(newOriginal);
		}
		return null;
	}

}
