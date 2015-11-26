package com.dexels.osgicompiler.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class WrappedClassLoader extends ClassLoader {

	private volatile ClassLoader parent;
	
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	public WrappedClassLoader() {
		readWriteLock.writeLock().lock();
	}
	private ClassLoader getClassLoaderParent() {
		
		readWriteLock.readLock().lock();
		ClassLoader result = parent;
		readWriteLock.readLock().unlock();
		return result;
		
	}
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
	       Class<?> loadedClass = findLoadedClass(name);
	       if(loadedClass!=null) {
	    	   return loadedClass;
	       }
	       return getClassLoaderParent().loadClass(name);
	       
//	       return super.findClass(name);
	}

	public void setClassLoader(ClassLoader parent) {
		this.parent = parent;
		readWriteLock.writeLock().unlock();
	}

	public void clearClassLoader(ClassLoader parent) {
		readWriteLock.writeLock().lock();
		this.parent = null;
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
        // First check whether it's already been loaded, if so use it
        Class<?> loadedClass = findLoadedClass(name);
        
        // Not loaded, try to load it 
        if (loadedClass == null) {
            try {
                // Ignore parent delegation and just try to load locally
                loadedClass = findClass(name);
            } catch (ClassNotFoundException e) {
                // Swallow exception - does not exist locally
            }
            
            // If not found locally, use normal parent delegation in URLClassloader
            if (loadedClass == null) {
                // throws ClassNotFoundException if not found in delegation hierarchy at all
                loadedClass = super.loadClass(name);
            }
        } 
        // will never return null (ClassNotFoundException will be thrown)
        return loadedClass;
	}

	@Override
	public URL getResource(String name) {
		return getClassLoaderParent().getResource(name);
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		return getClassLoaderParent().getResources(name);
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		return getClassLoaderParent().getResourceAsStream(name);
	}

	@Override
	public void setDefaultAssertionStatus(boolean enabled) {
		getClassLoaderParent().setDefaultAssertionStatus(enabled);
	}

	@Override
	public void setPackageAssertionStatus(String packageName, boolean enabled) {
		getClassLoaderParent().setPackageAssertionStatus(packageName, enabled);
	}

	@Override
	public void setClassAssertionStatus(String className, boolean enabled) {
		getClassLoaderParent().setClassAssertionStatus(className, enabled);
	}

	@Override
	public void clearAssertionStatus() {
		getClassLoaderParent().clearAssertionStatus();
	}

}
