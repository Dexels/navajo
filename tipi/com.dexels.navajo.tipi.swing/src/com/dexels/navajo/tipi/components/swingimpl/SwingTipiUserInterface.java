/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Locale;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RootPaneContainer;

import com.dexels.navajo.tipi.swingclient.UserInterface;
import com.dexels.navajo.tipi.swingclient.components.BaseWindow;
import com.dexels.navajo.tipi.swingclient.components.CopyCompatible;
import com.dexels.navajo.tipi.swingclient.components.PasteCompatible;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class SwingTipiUserInterface implements UserInterface {
	private final SwingTipiContext myContext;

	/**
	 * This is an extra offset to make sure the bottom will not be behind the
	 * status bar Not nice.
	 */
	private final int startBarHeight = 26;

	public SwingTipiUserInterface(SwingTipiContext s) {
		myContext = s;
	}

	@Override
	public JFrame getMainFrame() {
		return (JFrame) myContext.getTopLevel();
	}

	@Override
	public JDialog getTopDialog() {
		RootPaneContainer topDialog = myContext.getTopDialog();
		if (topDialog instanceof JDialog) {
			return (JDialog) topDialog;
		}
		return null;
	}

	@Override
	public void addDialog(JDialog d) {
		// d.setLocationRelativeTo(getMainFrame());
		myContext.addDialog(d);
		// d.pack();
		d.getContentPane().repaint();
		showCenteredDialog(d);
	}

	public void showDialogAt(JDialog dlg, int x, int y) {
		Dimension dlgSize = dlg.getPreferredSize();
		dlg.setLocation(x, y);

		if (dlgSize.height > (Toolkit.getDefaultToolkit().getScreenSize().height - startBarHeight)) {
			dlgSize.height = Toolkit.getDefaultToolkit().getScreenSize().height
					- startBarHeight;
			dlg.setSize(dlgSize);
		}

		if (dlgSize.width > Toolkit.getDefaultToolkit().getScreenSize().width) {
			dlgSize.width = Toolkit.getDefaultToolkit().getScreenSize().width;
			dlg.setSize(dlgSize);
		}

		dlg.setModal(true);
		dlg.setVisible(true);

	}

	public RootPaneContainer getRootPaneContainer() {
		return (RootPaneContainer) myContext.getTopLevel();
	}

	public void showCenteredDialog(JDialog dlg) {
		Dimension dlgSize = dlg.getBounds().getSize();
		Rectangle r = getRootPaneContainer().getRootPane().getBounds();
		Dimension frmSize = new Dimension(r.width, r.height);
		Point loc = getRootPaneContainer().getRootPane().getLocation();
		int x = Math.max(0, (frmSize.width - dlgSize.width) / 2 + loc.x + r.x);
		int y = Math
				.max(0, (frmSize.height - dlgSize.height) / 2 + loc.y + r.y);
		dlg.setLocation(x, y);

		if (dlgSize.height > (Toolkit.getDefaultToolkit().getScreenSize().height - startBarHeight)) {
			dlgSize.height = Toolkit.getDefaultToolkit().getScreenSize().height
					- startBarHeight;
			dlg.setSize(dlgSize);
		}

		if (dlgSize.width > Toolkit.getDefaultToolkit().getScreenSize().width) {
			dlgSize.width = Toolkit.getDefaultToolkit().getScreenSize().width;
			dlg.setSize(dlgSize);
		}

		dlg.setModal(true);
		dlg.setVisible(true);
	}

	@Override
	public boolean showQuestionDialog(String s) {
		int response = JOptionPane.showConfirmDialog(
				(Component) myContext.getTopLevel(), s, "",
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		return response == 0;
	}

	public void showInfoDialog(String s) {
		JOptionPane.showConfirmDialog((Component) myContext.getTopLevel(), s,
				"", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}

	public boolean areYouSure() {
		return showQuestionDialog("Are you sure?");
	}

	@Override
	public void clearClipboard() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeWindow(BaseWindow d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void copyToClipBoard(CopyCompatible cc) {
		// TODO Auto-generated method stub

	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object pasteFromClipBoard(PasteCompatible pc) {
		// TODO Auto-generated method stub
		return null;
	}

}
