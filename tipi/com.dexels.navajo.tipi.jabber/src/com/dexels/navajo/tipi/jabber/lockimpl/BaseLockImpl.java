package com.dexels.navajo.tipi.jabber.lockimpl;

import java.beans.*;
import java.util.*;

import com.dexels.navajo.tipi.jabber.*;

public abstract  class BaseLockImpl implements Lock {

//	protected final String id;
	private final List<PropertyChangeListener> myPropertyListeners = new ArrayList<PropertyChangeListener>();
	
//	public BaseLockImpl(String id) {
//		this.id = id;
//	}
//	
	public void addLockingListener(PropertyChangeListener p) {
		System.err.println("Added locklistener");
		myPropertyListeners.add(p);
	}



	public void removeLockingListener(PropertyChangeListener p) {
		myPropertyListeners.remove(p);
	}

	protected void fireLockingChanges(boolean locked, boolean oldLocked, String resource) {
		for (PropertyChangeListener p : myPropertyListeners) {
			p.propertyChange(new PropertyChangeEvent(this,"locked",oldLocked,locked));
		}
	}


}
