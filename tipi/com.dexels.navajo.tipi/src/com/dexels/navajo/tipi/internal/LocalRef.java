package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.tipi.TipiComponent;

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

public class LocalRef implements TipiReference {
	private final String name;
	private final TipiComponent myComponent;

	public LocalRef(String name, TipiComponent component) {
		this.name = name;
		myComponent = component;
	}

	public void setValue(Object expression) {
		myComponent.setLocalValue(name, expression);
	}

	public Object getValue() {
		return myComponent.getLocalValue(name);
	}
}
