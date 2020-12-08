/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
