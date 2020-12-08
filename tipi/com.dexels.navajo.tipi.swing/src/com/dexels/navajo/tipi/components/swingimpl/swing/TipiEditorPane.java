/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.io.IOException;

import javax.swing.JEditorPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;

public class TipiEditorPane extends JEditorPane {

	private static final long serialVersionUID = 4203512006059913217L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiEditorPane.class);
	
	private Binary myBinary = null;

	public TipiEditorPane() {
		setText("<html>hoei</html>");
	}

	public void setBinary(Binary b) throws IOException {
		myBinary = b;
		String string = b.getURL().toString();
		logger.debug("Setting: " + string);
		setPage(string);
	}

	public Binary getBinary() {
		return myBinary;
	}

}
