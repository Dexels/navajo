package com.dexels.navajo.tipi.dev.server.appmanager;

import java.io.IOException;

import com.dexels.navajo.repository.api.RepositoryInstance;

public interface AppStoreOperation {
	public void build(RepositoryInstance a) throws IOException;
}
