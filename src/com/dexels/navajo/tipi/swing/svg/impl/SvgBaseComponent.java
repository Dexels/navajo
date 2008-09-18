package com.dexels.navajo.tipi.swing.svg.impl;

import java.net.*;

import javax.swing.*;

import com.dexels.navajo.tipi.swing.svg.*;


public abstract class SvgBaseComponent extends JPanel {

	
	public SvgBaseComponent() {
		setOpaque(false);
	}

	public abstract void init(URL u);

	public abstract void fireAnimation(String animName);
//	public abstract void registerId(String id);
		   
	public abstract void addSvgAnimationListener(SvgAnimationListener sal);
	public abstract void removeSvgAnimationListener(SvgAnimationListener sal);
	public abstract void addSvgMouseListener(SvgMouseListener sal);
	public abstract void removeSvgMouseListener(SvgMouseListener sal);

	public abstract void setRegisteredIds(String object);
	

	public abstract void addSvgDocumentListener(SvgDocumentListener sal);
	public abstract void removeSvgDocumentListener(SvgDocumentListener sal);
	
	public abstract void setAttribute(final String ns, final String item, final String attributeName,final String value);
	public abstract void moveToFirst(final String id);
	public abstract void setTextContent(final String id, final String value);

	public abstract boolean isExisting(String name);
	public abstract String getTagName(String name);
	
}
