package com.dexels.osgicompiler;

import java.io.IOException;
import java.io.InputStream;

public interface OSGiJavaCompiler {
	public byte[] compile(String className, InputStream source) throws IOException;

	public void flushCompilerCache();
}
