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

	// private Object parseBorder(String s) {
	// return parseBorder(s);
	// }

	public Object parseCookie(String s, TipiComponent source) {
		if (s == null) {
			return null;
		}
		return source.getContext().getCookie(s);
		// Command c = new Command(){};
		// ContainerContext containerContext = (ContainerContext)
		// ((EchoTipiContext
		// )myContext).getInstance().getContextProperty(ContainerContext
		// .CONTEXT_PROPERTY_NAME);
		// if(containerContext==null) {
		// System.err.println("No containerContext!");
		// }
		// Cookie[] cc = containerContext.getCookies();
		//
		// for (int i = 0; i < cc.length; i++) {
		// if(cc[i].getName().equals(s)) {
		// return cc[i].getValue();
		// }
		// }
		// return null;
	}

}
