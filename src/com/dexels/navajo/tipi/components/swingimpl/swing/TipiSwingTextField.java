package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingTextField
    extends JTextField
     {

	@Override
	public void setText(String t) {
		String old = getText();
		if(t==null) {
			if(old==null) {
				return;
			}
		} else {
			if(t.equals(old)) {
				return;
			}
			
		}
		super.setText(t);
	}

}
