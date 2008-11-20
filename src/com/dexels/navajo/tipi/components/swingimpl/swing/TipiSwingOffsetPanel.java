package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class TipiSwingOffsetPanel extends JPanel {

	private int x;
	private int y;
	private JPanel myClient = new JPanel();
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public void setX(int x) {
		int old = this.x;
		this.x = x;
		layoutOffsetPanel();
		firePropertyChange("x",old, x);
	}


	public void setY(int y) {
		int old = this.y;
		this.y = y;
		layoutOffsetPanel();
		firePropertyChange("y",old, y);
	}

	public JPanel getClient() {
		return myClient;
	}



	public TipiSwingOffsetPanel() {
//		setLayout(null);
		add(myClient);
		addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e) {
				System.err.println("Bounds: "+getBounds());
				System.err.println("clBounds: "+myClient.getBounds());
				layoutOffsetPanel();
			}

			public void componentShown(ComponentEvent e) {
				layoutOffsetPanel();
			}});
	}
	
//	public void doLayout() {
//		super.doLayout();
//		layoutOffsetPanel();
//	}

	private void layoutOffsetPanel() {
		myClient.doLayout();
		myClient.setBounds(new Rectangle(getLocation().x+ x, getLocation().y+y,myClient.getPreferredSize().width,myClient.getPreferredSize().height));
//		myClient.setLocation(x, y);
	}
}
