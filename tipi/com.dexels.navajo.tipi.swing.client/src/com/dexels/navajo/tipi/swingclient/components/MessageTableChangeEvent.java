/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.event.ChangeEvent;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels.com
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 */

public class MessageTableChangeEvent extends ChangeEvent {

	private static final long serialVersionUID = -5496860644042474933L;
	private int myRow = -1;
	private int myColumn = -1;

	public MessageTableChangeEvent(Object o) {
		super(o);
	}

	public MessageTableChangeEvent(Object o, int row, int column) {
		super(o);
		myRow = row;
		myColumn = column;
	}

	public final int getColumn() {
		return myColumn;
	}

	public final int getRow() {
		return myRow;
	}
}