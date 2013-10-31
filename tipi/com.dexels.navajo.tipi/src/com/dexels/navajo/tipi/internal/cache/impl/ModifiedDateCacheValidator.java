package com.dexels.navajo.tipi.internal.cache.impl;

import java.io.IOException;

import com.dexels.navajo.tipi.internal.cache.CacheValidator;
import com.dexels.navajo.tipi.internal.cache.LocalStorage;
import com.dexels.navajo.tipi.internal.cache.RemoteStorage;

public class ModifiedDateCacheValidator implements CacheValidator {

	private LocalStorage localStorage;
	private RemoteStorage remoteStorage;
	
	@Override
	public boolean isLocalValid(String location) throws IOException {
		// TODO Auto-generated method stub
		long remote = remoteStorage.getRemoteModificationDate(location);
		long local = localStorage.getLocalModificationDate(location);
		return local>remote;
	}

	@Override
	public void setLocalStorage(LocalStorage localStorage) {
		this.localStorage = localStorage;
	}

	@Override
	public void setRemoteStorage(RemoteStorage remoteStorage) {
		this.remoteStorage = remoteStorage;
	}
	

}
