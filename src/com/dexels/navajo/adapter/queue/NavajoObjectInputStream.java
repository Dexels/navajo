package com.dexels.navajo.adapter.queue;

import java.io.*;

public class NavajoObjectInputStream extends ObjectInputStream {

	private ClassLoader classLoader;

	public NavajoObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException {
		super(in);
		this.classLoader = classLoader;
	}

	protected Class resolveClass(ObjectStreamClass desc) throws ClassNotFoundException {
		return Class.forName(desc.getName(), false, classLoader);
	}
}

