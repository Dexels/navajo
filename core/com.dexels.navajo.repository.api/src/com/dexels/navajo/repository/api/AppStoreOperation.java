package com.dexels.navajo.repository.api;

import java.io.IOException;

import com.dexels.navajo.repository.api.RepositoryInstance;

public interface AppStoreOperation {
	public void build(RepositoryInstance a) throws IOException;

	public String getRepoType();

	public String getType();
}
