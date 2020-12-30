package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;

public interface NS3Compatible {

	public void formatNS3(int indent, OutputStream w) throws IOException;
			
}
