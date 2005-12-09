package com.dexels.navajo.tipi.components.echoimpl.impl;

import nextapp.echo2.app.LayoutData;

public class XYLayoutConstraint implements LayoutData {
	private final int x, y, w, h;

	public XYLayoutConstraint(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public XYLayoutConstraint(int x, int y) {
		this.x = x;
		this.y = y;
		this.w = -1;
		this.h = -1;
	}

	public int getH() {
		return h;
	}

	public int getW() {
		return w;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String toString() {
		return x + "," + y + "," + w + "," + h;
	}
}
