package com.dexels.navajo.tipi.components.swingimpl.swing;

import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.swingimpl.dnd.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingTextField
    extends JTextField implements TipiDndCapable
     {

	private final TipiDndManager myDndManager;
	
	public TipiSwingTextField(TipiComponent component) {
		myDndManager = new TipiDndManager(this,component);
	}
	
	
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

	public TipiDndManager getDndManager() {
		return myDndManager;
	}

}
