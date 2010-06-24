package com.dexels.navajo.rhino;

import java.io.IOException;
import java.io.Writer;

public class IndentWriter {
	
	private static final int INDENT = 3;
	private final Writer myWriter;

	public IndentWriter(Writer w) {
		myWriter = w;
	}

	public void writeln(String line) throws IOException {
		write(line);
		myWriter.write('\n');
	}

	/**
	 * Will write indent + the line, but no newline
	 * @param line
	 * @throws IOException
	 */
	public void write(String line) throws IOException {
		for (int i = 0; i < indent; i++) {
			myWriter.write(' ');
		}
		myWriter.write(line);
	}

	private int indent = 0;

	public int getIndent() {
		return indent;
	}

	public void setIndent(int indent) {
		this.indent = indent;
	}
	
	public void in() {
		indent += INDENT;
	}

	public void out() {
		indent -= INDENT;
		if(indent<0) {
			throw new IllegalStateException("Negative indentation. Bad sign!");
		}
	}

}
