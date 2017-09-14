package com.dexels.navajo.document.stream.api;

import java.io.IOException;
import java.io.Writer;

public class Method {

	public final String name;

	public Method(String name) {
		this.name = name;
	}

	public void write(Writer sw, int indent) throws IOException {
		 for (int a = 0; a < indent; a++) {
			 sw.write(" ");
		 }
		 sw.write("<method name=\""+this.name+"\"/>\n");
	}
}
