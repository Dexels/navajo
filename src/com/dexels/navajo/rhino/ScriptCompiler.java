package com.dexels.navajo.rhino;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public interface ScriptCompiler {

	public void compileTsl(Reader is, IndentWriter os) throws IOException;

}
