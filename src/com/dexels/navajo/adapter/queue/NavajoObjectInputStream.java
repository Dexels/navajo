package com.dexels.navajo.adapter.queue;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * Class that supports Navajo classloaders (classloaders that support hot-pluggable jar-files)
 * 
 * @author arjen
 *
 */
public final class NavajoObjectInputStream extends ObjectInputStream {

	private ClassLoader classLoader;

	public NavajoObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException {
		super(in);
		this.classLoader = classLoader;
	}

	@SuppressWarnings("unchecked")
	protected Class resolveClass(ObjectStreamClass desc) throws ClassNotFoundException {
		return Class.forName(desc.getName(), false, classLoader);
	}
}

