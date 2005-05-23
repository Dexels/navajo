/*
 * Created on May 23, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dexels.navajo.loader;

import java.io.File;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NavajoBasicClassLoader extends NavajoClassSupplier {

	/* (non-Javadoc)
	 * @see com.dexels.navajo.loader.NavajoClassSupplier#getClass(java.lang.String)
	 */
	public Class getClass(String className) throws ClassNotFoundException {
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
	public Class getCompiledNavaScript(String className)
			throws ClassNotFoundException {
		String conv = className.replaceAll("/",".");
		return getClass(conv);
	}

}
