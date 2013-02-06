package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Point;

import javax.swing.JViewport;

public class TipiSwingViewport extends JViewport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1077745085517516479L;
	private int gridWidth;
	private int gridHeight;

	public void setX(int x) {
		int width = getSize().width;
		setViewPosition(new Point(Math.min(x, width), 0));
	}

	public void setY(int y) {
		int height = getSize().height;
		setViewPosition(new Point(0, Math.min(y, height)));
	}

	@Override
	public void setViewPosition(Point p) {
		super.setViewPosition(p);
	}

	public int getGridWidth() {
		return gridWidth;
	}

	public void setGridWidth(int gridWidth) {
		int old = this.gridWidth;
		this.gridWidth = gridWidth;
		firePropertyChange("gridWidth", old, gridWidth);
	}

	public int getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(int gridHeight) {
		int old = this.gridHeight;
		this.gridHeight = gridHeight;
		firePropertyChange("gridHeight", old, gridHeight);
	}

	public float getRelativeScrollX() {
		return getX() / getWidth();
	}

	public void setRelativeScrollX(float relativeScrollX) {
		setX((int) (getWidth() * relativeScrollX));
	}

	public float getRelativeScrollY() {
		return getY() / getHeight();
	}

	public void setRelativeScrollY(float relativeScrollY) {
		setY((int) (getHeight() * relativeScrollY));
	}

}
