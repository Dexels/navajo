package com.dexels.navajo.tipi.components.swingimpl.layout;

import java.awt.*;
import java.util.*;

import javax.swing.*;

public class NullLayout implements LayoutManager2 {

	private Dimension mySize;
	private Map<Component,Rectangle> components = new HashMap<Component, Rectangle>();
	
	public NullLayout(Dimension size) {
		mySize = size;
	}

	public NullLayout() {
		mySize = new Dimension(100,100);
	}
	
	public void setWidth(int w) {
		mySize = new Dimension(w,mySize.height);
	}

	public void setHeight(int h) {
		mySize = new Dimension(mySize.width,h);
	}

	public void addLayoutComponent(Component arg0, Object arg1) {
		components.put(arg0, (Rectangle) arg1);
	}

	public float getLayoutAlignmentX(Container arg0) {
		return 0;
	}

	public float getLayoutAlignmentY(Container arg0) {
		return 0;
	}

	public void invalidateLayout(Container arg0) {
		
	}

	public Dimension maximumLayoutSize(Container arg0) {
		return null;
	}

	
	public void layoutContainer(Container parent) {
		for (Component c : components.keySet()) {
			Rectangle r = components.get(c);
			c.setBounds(r);
		}
	}

	public Dimension minimumLayoutSize(Container parent) {
		return null;
	}

	public Dimension preferredLayoutSize(Container parent) {
		return null;
	}

	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	public void addLayoutComponent(String name, Component comp) {
		System.err.println("que?");
	}

	
	public static void main(String[] args) {
		JFrame j = new JFrame();
		j.setSize(new Dimension(400,300));
		j.getContentPane().setLayout(new NullLayout());
		j.getContentPane().add(new JButton("woepie"),new Rectangle(10,100,50,100));
		j.getContentPane().add(new JButton("wappie"),new Rectangle(10,200,50,10));
		j.setVisible(true);
		}
	

}
