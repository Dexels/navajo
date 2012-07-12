package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.internal.TipiReference;

/**
 * s
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
public class CookieRef implements TipiReference {

	private final TipiContext myContext;
	private final String myKey;

	public CookieRef(String key, TipiContext myContext) {
		this.myContext = myContext;
		this.myKey = key;
	}

	public void setValue(Object val) {
		myContext.setCookie(myKey, (String) val);
	}

	public Object getValue() {
		return myContext.getCookie(myKey);
	}

}
