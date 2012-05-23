package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 * <p>
 * Title: Seperate project for Navajo Swing client
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Dexels
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class MessageTableFooter extends JTableHeader {
	private static final long serialVersionUID = -4750039572930815538L;

	public MessageTableFooter(JTable table, TableCellRenderer tc) {
		super.setReorderingAllowed(false);
		super.setTable(table);
		setDefaultRenderer(tc);
		// setBorder(null);
		setOpaque(true);
		// table.setOpaque(false);
	}

}
