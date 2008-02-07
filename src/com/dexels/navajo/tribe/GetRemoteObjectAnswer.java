package com.dexels.navajo.tribe;

import java.lang.ref.SoftReference;

public class GetRemoteObjectAnswer extends Answer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8723109407822703643L;
	
	private Object myObject;
	private boolean isSoftReference = false;
	
	public GetRemoteObjectAnswer(GetRemoteObjectRequest request, Object o) {
		super(request);
		
		if ( o instanceof SoftReference<?>) {
			myObject = ((SoftReference) o).get();
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
			return new SoftReference(myObject);
		}
	}

	public boolean isSoftReference() {
		return isSoftReference;
	}

}
