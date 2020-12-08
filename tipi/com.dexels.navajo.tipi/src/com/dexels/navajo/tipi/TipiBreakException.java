/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi;

//import com.dexels.navajo.document.nanoimpl.*;
/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiBreakException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1615937745926482378L;
	public static final int BREAK_BLOCK = 1;
	public static final int BREAK_EVENT = 2;

	public static final int COMPONENT_DISPOSED = 3;
	public static final int USER_BREAK = 4;
	public static final int WEBSERVICE_BREAK = 5;

	private int myType = BREAK_BLOCK;

	public TipiBreakException() {
	}

	public TipiBreakException(int type) {
		myType = type;
	}

	public int getType() {
		return myType;
	}
}