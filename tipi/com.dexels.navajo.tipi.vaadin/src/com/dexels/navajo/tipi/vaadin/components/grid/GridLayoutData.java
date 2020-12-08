/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components.grid;

import com.vaadin.ui.Alignment;

public class GridLayoutData {
	private Insets insets = null;
	private Alignment alignment = null;

	private int rowSpan = 1;
	private int columnSpan = 1;
	
	public void setInsets(Insets parseInsets) {
		insets = parseInsets;
	}

	public void setColumnSpan(int parseInt) {
		columnSpan = parseInt;
	}

	public void setRowSpan(int parseInt) {
		rowSpan = parseInt;
	}

	public int getColumnSpan() {
		return columnSpan;
	}

	public int getRowSpan() {
		return rowSpan;
	}

	@Override
	public String toString() {
		return "GRID: "+columnSpan+":"+rowSpan+":"+insets;
	}

	public void setAlignment(Alignment parseAlignment) {
		alignment = parseAlignment;		
	}
	
	public Alignment getAlignment(){
		return alignment;
	}
}
