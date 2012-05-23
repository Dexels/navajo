package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.io.IOException;

import javax.swing.JEditorPane;

import com.dexels.navajo.document.types.Binary;

public class TipiEditorPane extends JEditorPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4203512006059913217L;
	private Binary myBinary = null;

	public TipiEditorPane() {
		setText("<html>hoei</html>");
	}

	public void setBinary(Binary b) throws IOException {
		myBinary = b;
		String string = b.getURL().toString();
		System.err.println("Setting: " + string);
		setPage(string);
	}

	public Binary getBinary() {
		return myBinary;
	}

}
