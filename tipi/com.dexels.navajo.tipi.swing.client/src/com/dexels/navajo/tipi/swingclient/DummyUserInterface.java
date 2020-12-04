/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingclient;

import java.util.Locale;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.dexels.navajo.tipi.swingclient.components.BaseWindow;
import com.dexels.navajo.tipi.swingclient.components.CopyCompatible;
import com.dexels.navajo.tipi.swingclient.components.PasteCompatible;

//import com.dexels.sportlink.client.swing.*;

public class DummyUserInterface implements UserInterface {

	public DummyUserInterface() {
	}

	@Override
	public void addDialog(JDialog d) {
	}

	@Override
	public Locale getLocale() {
		return Locale.getDefault();
	}

	@Override
	public Object pasteFromClipBoard(PasteCompatible pc) {
		return null;
	}

	@Override
	public void copyToClipBoard(CopyCompatible cc) {
	}

	@Override
	public void clearClipboard() {
	}

	// public Preferences getPreferences() {
	// /**@todo: Implement this com.dexels.sportlink.client.swing.UserInterface
	// method*/
	// throw new
	// java.lang.UnsupportedOperationException("Method getPreferences() not yet implemented.");
	// }
	@Override
	public boolean showQuestionDialog(String s) {
		return true;
	}

	@Override
	public JFrame getMainFrame() {
		return null;
	}

	@Override
	public JDialog getTopDialog() {
		return null;
	}

	@Override
	public void closeWindow(BaseWindow d) {

	}

}
