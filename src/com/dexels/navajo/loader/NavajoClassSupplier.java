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
public abstract class NavajoClassSupplier extends ClassLoader {
	public abstract Class getClass(String className) throws ClassNotFoundException;
    public abstract File [] getJarFiles(String path, boolean beta);
    public abstract Class getCompiledNavaScript(String className)  throws ClassNotFoundException;
	
}
