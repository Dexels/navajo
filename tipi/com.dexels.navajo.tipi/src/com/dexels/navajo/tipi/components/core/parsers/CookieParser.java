package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiTypeParser;
import com.dexels.navajo.tipi.internal.TipiEvent;

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
public class CookieParser extends TipiTypeParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6511511870151734068L;

	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		return parseCookie(expression,source);
	}

	public Object parseCookie(String s, TipiComponent source) {
		if (s == null) {
			return null;
		}
		return source.getContext().getCookie(s);
	}

}
