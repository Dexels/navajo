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
public abstract class NavajoClassSupplier extends ClassLoader {
	@SuppressWarnings("unchecked")
	public abstract Class getClass(String className) throws ClassNotFoundException;
    public abstract File [] getJarFiles(String path, boolean beta);
    @SuppressWarnings("unchecked")
	public abstract Class getCompiledNavaScript(String className)  throws ClassNotFoundException;
	
    public NavajoClassSupplier(ClassLoader parent) {
    	super(parent);
    }
    
	public NavajoClassSupplier() {
		// TODO Auto-generated constructor stub
	}
}
