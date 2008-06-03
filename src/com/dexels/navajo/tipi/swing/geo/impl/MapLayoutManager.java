package com.dexels.navajo.tipi.swing.geo.impl;

import java.awt.*;

public class MapLayoutManager implements LayoutManager {

	public void addLayoutComponent(String constr, Component c) {
		System.err.println("La: "+constr);
	}

	public void layoutContainer(Container c) {
		// TODO Auto-generated method stub
		System.err.println("Laoy: "+c);
	}

	public Dimension minimumLayoutSize(Container c) {
		return null;
	}

	public Dimension preferredLayoutSize(Container c) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeLayoutComponent(Component c) {

	}

}
