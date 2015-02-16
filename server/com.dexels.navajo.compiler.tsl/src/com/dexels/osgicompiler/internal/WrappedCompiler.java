package com.dexels.osgicompiler.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dexels.osgicompiler.OSGiJavaCompiler;

public class WrappedCompiler implements OSGiJavaCompiler {

	private OSGiJavaCompiler parentJavaCompiler = null;

	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	public WrappedCompiler() {
		readWriteLock.writeLock().lock();
	}
	
	@Override
	public byte[] compile(String className, InputStream source)
			throws IOException {
		readWriteLock.readLock().lock();
		byte[] result = parentJavaCompiler.compile(className, source);
		readWriteLock.readLock().unlock();
		return result;
	}

	public void setJavaCompiler(OSGiJavaCompiler parent) {
		readWriteLock.writeLock().unlock();
		parentJavaCompiler = parent;
	}

	public void clearJavaCompiler(OSGiJavaCompiler parent) {
		readWriteLock.writeLock().lock();
		parentJavaCompiler = null;
	}

}
