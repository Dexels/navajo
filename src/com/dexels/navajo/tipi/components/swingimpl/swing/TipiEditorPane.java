package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.io.*;

import javax.swing.*;

import com.dexels.navajo.document.types.*;

public class TipiEditorPane extends JEditorPane {

	private Binary myBinary = null;
	public TipiEditorPane() {
		setText("<html>hoei</html>");
	}

	public void setBinary(Binary b) throws IOException {
		myBinary = b;
		String string = b.getURL().toString();
		System.err.println("Setting: "+string);
		setPage(string);
	}
	
	
	public Binary getBinary() {
		return myBinary;
	}
	
}
