/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.KeyStroke;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingMenu extends JMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7483992922585536025L;
	public static String STRINGMNEMONIC_CHANGED_PROPERTY = "string_mnemonic";

	public void setIconUrl(URL u) {
		setIcon(new ImageIcon(u));
	}

	public void setStringMnemonic(String s) {
		String old = getStringMnemonic();
		setMnemonic(s.charAt(0));
		firePropertyChange(STRINGMNEMONIC_CHANGED_PROPERTY, old, s);
	}

	public String getStringMnemonic() {
		return new String("" + (char) getMnemonic());
	}

	public void setAccelerator(String s) {
		setAccelerator(KeyStroke.getKeyStroke(s));
	}

}
