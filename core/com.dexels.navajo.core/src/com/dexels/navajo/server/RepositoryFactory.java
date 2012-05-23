package com.dexels.navajo.server;

public interface RepositoryFactory {

	public void addRepository(Repository r);

	public void removeRepository(Repository r);

	public Repository getRepository(String repository);

}