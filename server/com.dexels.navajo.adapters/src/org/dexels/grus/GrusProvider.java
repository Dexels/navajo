package org.dexels.grus;


public interface GrusProvider {

	public GrusConnection requestConnection(String instance, String name);
	public GrusConnection requestConnection(long id);
	public void release(GrusConnection grusDataSource);

}
