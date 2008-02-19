package com.dexels.navajo.tipi.components.swingimpl.swing;

public class TipiSwingGridPanel extends TipiSwingPanel {
	private String columnWidth = null;
	private int gridHeight;
	public String getColumnWidth() {
		return columnWidth;
	}
	public void setColumnWidth(String columnWidth) {
		String old = this.columnWidth;
		this.columnWidth = columnWidth;
		firePropertyChange("columnWidth", old, columnWidth);
	}

	public int getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(int gridHeight) {
		int old = this.gridHeight;
		this.gridHeight = gridHeight;
		firePropertyChange("gridHeight", old, columnWidth);
	}
	
}
