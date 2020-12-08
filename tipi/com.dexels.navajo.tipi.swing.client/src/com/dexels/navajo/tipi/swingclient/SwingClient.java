/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingclient;

import java.awt.BorderLayout;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.UIManager;

//import com.dexels.sportlink.client.swing.*;

public class SwingClient {

	private static UserInterface userInterface;

	// static {
	// setUserInterface(new DummyUserInterface());
	// }

	public static UserInterface getUserInterface() {
		return userInterface;
	}

	public static void setUserInterface(UserInterface u) {
		userInterface = u;
	}

	public static void main(String[] args) throws Exception {
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		UIManager.setLookAndFeel(UIManager
				.getCrossPlatformLookAndFeelClassName());

		JFrame jf = new JFrame();
		jf.getContentPane().setLayout(new BorderLayout());
		JDesktopPane desktopPane = new JDesktopPane();
		jf.getContentPane().add(desktopPane, BorderLayout.CENTER);
		jf.setSize(400, 300);
		JInternalFrame jif = new JInternalFrame("aap");
		desktopPane.add(jif);
		jf.setVisible(true);
		jif.setSize(300, 200);
		jif.setLocation(10, 20);
		jif.setVisible(true);
	}
}
