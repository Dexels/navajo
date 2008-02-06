package com.dexels.navajo.rich.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.plaf.ButtonUI;

public class ImageButtonUI extends ButtonUI{


	public void paint(Graphics g, JComponent c) {
		g.setColor(Color.red);
		g.fillRect(0, 0, c.getWidth(), c.getHeight());
	}

}
