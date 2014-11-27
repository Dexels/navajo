package com.dexels.navajo.repository.api;

import java.io.IOException;

public interface AppStoreOperation {
	public void build(RepositoryInstance a) throws IOException;

	public String getRepoType();

	public String getType();
	
	public String getName();
}
