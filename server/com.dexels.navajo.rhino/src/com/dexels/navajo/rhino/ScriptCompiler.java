package com.dexels.navajo.rhino;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ScriptCompiler {

	public void compileTsl(String scriptName, InputStream is, OutputStream os,
			StringBuffer compilerErrors) throws IOException;

}
