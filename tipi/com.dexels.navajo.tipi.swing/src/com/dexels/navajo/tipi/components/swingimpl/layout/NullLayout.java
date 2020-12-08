/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiComponent;

public class NullLayout implements LayoutManager2 {

	private Dimension mySize;
	private Map<Component, Rectangle> components = new HashMap<Component, Rectangle>();
	
	private final static Logger logger = LoggerFactory
			.getLogger(NullLayout.class);
	
	private Container parent = null;
//	private TipiXYLayout myLayout;
//	private TipiComponent myComponent;

	public NullLayout(Dimension size) {
		mySize = size;
	}

	public NullLayout() {
		mySize = new Dimension(100, 100);
	}

	public NullLayout(TipiXYLayout tipiXYLayout, TipiComponent myComponent) {
		this();
//		this.myComponent = myComponent;
//		this.myLayout = tipiXYLayout;
	}

	public void setWidth(int w) {
		mySize = new Dimension(w, mySize.height);
	}

	public void setHeight(int h) {
		mySize = new Dimension(mySize.width, h);
	}

	@Override
	public void addLayoutComponent(Component arg0, Object arg1) {
		components.put(arg0, (Rectangle) arg1);
	}

	@Override
	public float getLayoutAlignmentX(Container arg0) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container arg0) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container arg0) {

	}
	
	public Container getParent() {
		return parent;
	}

	@Override
	public Dimension maximumLayoutSize(Container parent) {
		return new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE);
	}

	@Override
	public void layoutContainer(Container parent) {
		this.parent = parent;
		for (Component c : components.keySet()) {
			Rectangle r = components.get(c);
			c.setBounds(r);
		}
	}

	public void doUpdate() {
		try {
			SwingUtilities.invokeAndWait(new Runnable(){

				@Override
				public void run() {
					layoutContainer(getParent());
				}});
		} catch (InvocationTargetException e) {
			logger.error("Error: ", e);
		} catch (InterruptedException e) {
			logger.error("Error: ", e);
		}
		
	}
	
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(0,0);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension d = calculateLayoutSize();
		return d;
	}

	private Dimension calculateLayoutSize() {
		int maxw = 0;
		int maxh = 0;
		for (Entry<Component, Rectangle> r : components.entrySet()) {
			int w = r.getValue().x + r.getValue().width;
			int h = r.getValue().y + r.getValue().height;
			if(w>maxw) {
				maxw = w;
			}
			if(h>maxh) {
				maxh = h;
			}
		}
		Dimension d = new Dimension(maxw,maxh);
		return d;
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		// logger.debug("que?");
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
//		logger.debug("pref: "+nullLayout.preferredLayoutSize(parent));
		j.setVisible(true);
	}

}
