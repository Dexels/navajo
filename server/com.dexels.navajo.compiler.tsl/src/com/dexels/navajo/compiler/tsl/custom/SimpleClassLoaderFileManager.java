package com.dexels.navajo.compiler.tsl.custom;

import java.io.IOException;
import java.util.Set;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

import org.osgi.framework.BundleContext;

public class SimpleClassLoaderFileManager extends ForwardingJavaFileManager<JavaFileManager> {

	public SimpleClassLoaderFileManager(BundleContext context, ClassLoader classLoader, JavaFileManager fileManager) {
		super(fileManager);
	}

	@Override
	public FileObject getFileForInput(Location location, String packageName,
			String relativeName) throws IOException {
		System.err.println("Package: "+packageName+" relative: "+relativeName);
		return super.getFileForInput(location, packageName, relativeName);
	}

	@Override
	public Iterable<JavaFileObject> list(Location location, String packageName,
			Set<Kind> kinds, boolean recurse) throws IOException {
		System.err.println("Listing packageName: "+packageName);
		return super.list(location, packageName, kinds, recurse);
	}

	
}
