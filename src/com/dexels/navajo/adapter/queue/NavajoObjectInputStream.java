package com.dexels.navajo.adapter.queue;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public final class NavajoObjectInputStream extends ObjectInputStream {

	private ClassLoader classLoader;

	public NavajoObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException {
		super(in);
		this.classLoader = classLoader;
	}

	protected Class resolveClass(ObjectStreamClass desc) throws ClassNotFoundException {
		return Class.forName(desc.getName(), false, classLoader);
	}
}

