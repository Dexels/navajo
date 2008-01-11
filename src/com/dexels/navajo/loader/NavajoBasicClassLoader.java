/*
 * Created on May 23, 2005
 *
 */
package com.dexels.navajo.loader;

import java.io.File;

/**
 * @author Administrator
 *
 */
public class NavajoBasicClassLoader extends NavajoClassSupplier {

	public NavajoBasicClassLoader(ClassLoader parent) {
		super(parent);
	}
	/* (non-Javadoc)
	 * @see com.dexels.navajo.loader.NavajoClassSupplier#getClass(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Class getClass(String className) throws ClassNotFoundException {
//	    System.err.println("Basic classloader supplying: "+className);
		return Class.forName(className,true,this);
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.loader.NavajoClassSupplier#getJarFiles(java.lang.String, boolean)
	 */
	public File[] getJarFiles(String path, boolean beta) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.loader.NavajoClassSupplier#getCompiledNavaScript(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Class getCompiledNavaScript(String className)
			throws ClassNotFoundException {
		String conv = className.replaceAll("/",".");
		return getClass(conv);
	}

}
