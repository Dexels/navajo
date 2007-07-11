package com.dexels.navajo.tipi.internal;

import com.dexels.navajo.tipi.*;

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

	public void setValue(Object expression, TipiComponent tc) {
		System.setProperty(name, "" + expression);
	}
}
