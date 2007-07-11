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
public class TipiBreakException extends Exception {
	public static final int BREAK_BLOCK = 1;
	public static final int BREAK_EVENT = 2;
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