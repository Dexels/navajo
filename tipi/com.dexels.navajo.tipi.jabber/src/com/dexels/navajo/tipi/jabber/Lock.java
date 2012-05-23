package com.dexels.navajo.tipi.jabber;

import java.beans.*;

public interface Lock  {
	
	
	public boolean setLockRequest(String resource);
	public void unlock();
	public boolean isLocked();
	public void addLockingListener(PropertyChangeListener p);
	public void removeLockingListener(PropertyChangeListener p);
}
