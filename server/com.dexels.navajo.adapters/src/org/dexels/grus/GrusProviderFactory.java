package org.dexels.grus;

public class GrusProviderFactory {
	private static GrusProvider provider = null;
	
	public static void setInstance(GrusProvider p ) {
		provider = p;
	}
	
	public static GrusProvider getInstance() {
		return provider;
	}
}
