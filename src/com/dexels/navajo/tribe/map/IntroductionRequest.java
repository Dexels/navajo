package com.dexels.navajo.tribe.map;

import java.lang.ref.SoftReference;

import com.dexels.navajo.tribe.Answer;
import com.dexels.navajo.tribe.Request;

public class IntroductionRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3677395808956459975L;
	
	private SharedTribalMap stm = null;
	private boolean hasSoftReferences = false;
	
	public IntroductionRequest(SharedTribalMap stm) {
		super.blocking = false;
		// Check for SoftReference values...
		java.util.Iterator iter = stm.values().iterator();
		if ( iter.hasNext() ) {
			if ( iter.next() instanceof SoftReference<?>) {
				hasSoftReferences = true;
			}
		}
		if ( hasSoftReferences ) {
			System.err.println("Constructing Map WITHOUT soft references...");
			SharedTribalMap copy = new SharedTribalMap(stm.getId());
			iter = stm.keySet().iterator();
			while ( iter.hasNext() ) {
				Object key = iter.next();
				SoftReference<?> value = (SoftReference<?>) stm.get(key);
				if ( value != null ) {
					copy.putLocal(key, value.get());
				}
			}
			this.stm = copy;
		} else {
			this.stm = stm;
		}
	}
	
	@Override
	public Answer getAnswer() {
		System.err.println("Registering SharedTribalMap: " + stm.getId());
		if ( !hasSoftReferences ) {
			System.err.println("NO Soft references...");
			SharedTribalMap.registerMapLocal(stm);
		} else {
			System.err.println("HAS Soft references...");
			SharedTribalMap newOriginal = new SharedTribalMap(stm.getId());
			java.util.Iterator iter = stm.keySet().iterator();
			while ( iter.hasNext() ) {
				Object key = iter.next();
				Object value = stm.get(key);
				System.err.println("CREATING ENTRY WITH KEY: " + key);
				newOriginal.putLocal(key, new SoftReference(value));
			}
			SharedTribalMap.registerMapLocal(newOriginal);
		}
		return null;
	}

}
