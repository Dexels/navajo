package com.dexels.navajo.tipi.swing.svg.impl;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import com.dexels.navajo.tipi.swing.svg.*;


public abstract class SvgBaseComponent extends JPanel {

	
	public SvgBaseComponent() {
	
	}

	public abstract void init(URL u);

	public abstract void fireAnimation(String animName);
//	public abstract void registerId(String id);
		  
	public abstract void addSvgAnimationListener(SvgAnimationListener sal);
	public abstract void removeSvgAnimationListener(SvgAnimationListener sal);
	public abstract void addSvgMouseListener(SvgMouseListener sal);
	public abstract void removeSvgMouseListener(SvgMouseListener sal);

	public abstract void setRegisteredIds(String object);
	
}
