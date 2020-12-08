/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
