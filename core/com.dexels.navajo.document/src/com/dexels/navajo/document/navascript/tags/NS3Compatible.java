package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public interface NS3Compatible {

	public void writeNS3(int indent, OutputStream w) throws IOException;
	
}
