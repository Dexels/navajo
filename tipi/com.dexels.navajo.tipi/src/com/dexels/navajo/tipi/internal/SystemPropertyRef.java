package com.dexels.navajo.tipi.internal;

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

public class SystemPropertyRef implements TipiReference {

	private final String name;

	public SystemPropertyRef(String name) {
		this.name = name;
	}

	@Override
	public void setValue(Object expression) {
		System.setProperty(name, "" + expression);
	}

	@Override
	public Object getValue() {
		return System.getProperty(name);
	}
}
