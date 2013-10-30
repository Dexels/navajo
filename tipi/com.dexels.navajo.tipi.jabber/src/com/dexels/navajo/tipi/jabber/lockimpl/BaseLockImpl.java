package com.dexels.navajo.tipi.jabber.lockimpl;

import java.beans.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.jabber.*;

public abstract  class BaseLockImpl implements Lock {

	private final List<PropertyChangeListener> myPropertyListeners = new ArrayList<PropertyChangeListener>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(BaseLockImpl.class);
	
	@Override
	public void addLockingListener(PropertyChangeListener p) {
		logger.info("Added locklistener");
		myPropertyListeners.add(p);
	}



	@Override
	public void removeLockingListener(PropertyChangeListener p) {
		myPropertyListeners.remove(p);
	}

	protected void fireLockingChanges(boolean locked, boolean oldLocked) {
		for (PropertyChangeListener p : myPropertyListeners) {
			p.propertyChange(new PropertyChangeEvent(this,"locked",oldLocked,locked));
		}
	}


}
