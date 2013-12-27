package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.tipi.TipiContext;

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

public class GlobalRef implements TipiReference {
	private final String name;
	private final TipiContext myContext;

	public GlobalRef(String name, TipiContext context) {
		this.name = name;
		myContext = context;
	}

	@Override
	public void setValue(Object expression) {
		myContext.setGlobalValue(name, expression);
	}

	@Override
	public Object getValue() {
		return myContext.getGlobalValue(name);
	}
}
