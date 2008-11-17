package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import java.io.*;
import java.net.*;

import javax.imageio.*;
import javax.swing.*;

import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.swingimpl.dnd.*;

public class TipiSwingButton extends JButton implements TipiDndCapable {
	
	public static String STRINGMNEMONIC_CHANGED_PROPERTY = "string_mnemonic"; 
	
	private final TipiDndManager myDndManager;
	
	public TipiSwingButton(TipiComponent component) {
		myDndManager = new TipiDndManager(this,component);
	}
	

	public void setPreferredSize(Dimension d) {
		// ignore.
	}

	
	
	@Override
	public void setCursor(Cursor cursor) {
		System.err.println("Setting cursor: "+cursor.getName());
		Thread.dumpStack();
		super.setCursor(cursor);
	}


	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public void setIconUrl(Object u) {
		setIcon(getIcon(u));
	}

	 protected ImageIcon getIcon(Object u) {
		 if(u==null) {
			 return null;
		 }
		 if(u instanceof URL) {
			   return new ImageIcon((URL) u);
		 }
		 if(u instanceof Binary) {
			 Image i;
			try {
				i = ImageIO.read(((Binary) u).getDataAsStream());
				 ImageIcon ii = new ImageIcon(i);
				 return ii;
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		 return null;
	  }

	public void setStringMnemonic(String s) {
		String old = getStringMnemonic();
		setMnemonic(s.charAt(0));
		firePropertyChange(STRINGMNEMONIC_CHANGED_PROPERTY, old, s);
	}

	public String getStringMnemonic() {
		return new String("" + (char) getMnemonic());
	}

	public void setMnemonic(String s) {
		setMnemonic(s.charAt(0));
	}


	public TipiDndManager getDndManager() {
		return myDndManager;
	}

}
