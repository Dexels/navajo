package org.dexels.grus;

import com.dexels.navajo.script.api.UserException;


public interface GrusProvider {

	public GrusConnection requestConnection(String instance, String name) throws UserException;
	public GrusConnection requestReuseThreadConnection(String instance, String name) throws UserException;
	public boolean threadContainsConnection(String instance, String name);

	public GrusConnection requestConnection(long id);
	public void release(GrusConnection grusDataSource);
}
