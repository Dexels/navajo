package com.dexels.navajo.tipi;

import com.dexels.navajo.tipi.internal.*;

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
public abstract class TipiTypeParser {
	private Class<?> myReturnType = null;

	/**
	 * Note to implementers: EXPRESSION CAN BE NULL. Deal with it.
	 * 
	 * @param source
	 * @param expression
	 * @param event
	 * @return
	 */
	public abstract Object parse(TipiComponent source, String expression, TipiEvent event);

	protected TipiContext myContext;

	public void setContext(TipiContext tc) {
		myContext = tc;
	}

	public Class<?> getReturnType() {
		return myReturnType;
	}

	public void setReturnType(Class<?> c) {
		myReturnType = c;
	}

	public String toString(Object o, TipiComponent source) {
		return o == null ? "null" : o.toString();
	}
}
