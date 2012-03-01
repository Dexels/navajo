package com.dexels.navajo.tipi.components.swingimpl.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.dexels.navajo.tipi.TipiComponent;

public class NullLayout implements LayoutManager2 {

	private Dimension mySize;
	private Map<Component, Rectangle> components = new HashMap<Component, Rectangle>();

	private TipiXYLayout myLayout;
	private TipiComponent myComponent;

	public NullLayout(Dimension size) {
		mySize = size;
	}

	public NullLayout() {
		mySize = new Dimension(100, 100);
	}

	public NullLayout(TipiXYLayout tipiXYLayout, TipiComponent myComponent) {
		this();
		this.myComponent = myComponent;
		this.myLayout = tipiXYLayout;
	}

	public void setWidth(int w) {
		mySize = new Dimension(w, mySize.height);
	}

	public void setHeight(int h) {
		mySize = new Dimension(mySize.width, h);
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

	public Dimension maximumLayoutSize(Container parent) {
		return parent.getMaximumSize();
	}

	public void layoutContainer(Container parent) {
		for (Component c : components.keySet()) {
			Rectangle r = components.get(c);
			c.setBounds(r);
		}
	}

	public Dimension minimumLayoutSize(Container parent) {
		return parent.getMinimumSize();
	}

	public Dimension preferredLayoutSize(Container parent) {
		return parent.getPreferredSize();
	}

	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	public void addLayoutComponent(String name, Component comp) {
		// System.err.println("que?");
	}

	public static void main(String[] args) {
		JFrame j = new JFrame();
		j.setSize(new Dimension(400, 300));
		NullLayout nullLayout = new NullLayout();
		j.getContentPane().setLayout(nullLayout);
		j.getContentPane().add(new JButton("woepie"),
				new Rectangle(10, 100, 50, 100));
		JButton wappie = new JButton("wappie");
		j.getContentPane().add(wappie,
				new Rectangle(10, 200, 50, 10));
//		System.err.println("pref: "+nullLayout.preferredLayoutSize(parent));
		j.setVisible(true);
	}

}
