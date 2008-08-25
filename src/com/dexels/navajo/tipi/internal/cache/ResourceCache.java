package com.dexels.navajo.tipi.internal.cache;

import java.util.*;

public interface ResourceCache extends LocalStorage{
	public Date getRemoteModificationDate(String location);
	public boolean isUpToDate(String location);
	
}
