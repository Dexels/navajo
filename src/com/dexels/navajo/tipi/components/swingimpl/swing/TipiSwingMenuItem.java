package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
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
public class TipiSwingMenuItem extends JMenuItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3647531512362433699L;
	public static String STRINGMNEMONIC_CHANGED_PROPERTY = "string_mnemonic";

	public TipiSwingMenuItem() {
	}

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
