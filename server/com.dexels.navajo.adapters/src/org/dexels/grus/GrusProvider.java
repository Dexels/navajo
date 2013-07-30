package org.dexels.grus;

import com.dexels.navajo.script.api.UserException;


public interface GrusProvider {

	public GrusConnection requestConnection(String instance, String name, String username) throws UserException;
	public GrusConnection requestConnection(long id);
	public void release(GrusConnection grusDataSource);

}
