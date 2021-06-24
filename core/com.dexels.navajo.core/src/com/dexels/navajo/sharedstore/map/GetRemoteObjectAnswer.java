/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.sharedstore.map;

import java.lang.ref.SoftReference;

import com.dexels.navajo.server.enterprise.tribe.Answer;

public class GetRemoteObjectAnswer extends Answer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8723109407822703643L;
	
	private Object myObject;
	private boolean isSoftReference = false;
	
	GetRemoteObjectAnswer(GetRemoteObjectRequest request, Object o) {
		super(request);
		
		if ( o instanceof SoftReference<?>) {
			myObject = ((SoftReference<?>) o).get();
			isSoftReference = true;
		} else {
			myObject = o;
			isSoftReference = false;
		}
	}
	
	@Override
	public boolean acknowledged() {
		return true;
	}

	public Object getObject() {
		if ( !isSoftReference ) {
			return myObject;
		} else {
			return new SoftReference<Object>(myObject);
		}
	}

	public boolean isSoftReference() {
		return isSoftReference;
	}

}
