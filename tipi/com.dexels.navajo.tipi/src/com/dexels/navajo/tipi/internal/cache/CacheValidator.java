package com.dexels.navajo.tipi.internal.cache;

import java.io.IOException;

public interface CacheValidator {
	public boolean isLocalValid(String location) throws IOException;

	public void setLocalStorage(LocalStorage localStorage);

	public void setRemoteStorage(RemoteStorage remoteStorage);
}
