package com.dexels.navajo.tipi.components.swingimpl.swing;

import com.dexels.navajo.tipi.TipiComponent;

public class TipiSwingGridPanel extends TipiSwingPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2598581413083873040L;
	private String columnWidth = null;
	private int gridHeight;

	public TipiSwingGridPanel(TipiComponent source) {
		super(source);
	}
	
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
