package com.dexels.osgicompiler;

import java.io.IOException;
import java.io.InputStream;

import com.dexels.navajo.script.api.CompilationException;

public interface OSGiJavaCompiler {
	public byte[] compile(String className, InputStream source)
			throws IOException, CompilationException;

}
