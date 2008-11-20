package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;

import javax.swing.*;

public class TipiSwingViewport extends JViewport {
	
	public void setX(int x) {
		int width = getSize().width;
		setViewPosition(new Point(Math.min(x, width), 0));
	}

	public void setY(int y) {
		int height = getSize().height;
		System.err.println("h: "+height+" y: "+y);
		setViewPosition(new Point(0, Math.min(y, height)));
	}


}
