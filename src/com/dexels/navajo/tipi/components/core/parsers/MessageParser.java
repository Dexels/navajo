package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
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
public class MessageParser extends BaseTipiParser {
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		 System.err.println("PAESING " + expression);
		Message m = getMessageByPath(source, expression);
		// if (m != null) {
		// m.write(System.err);
		// }
		return m;
	}

	public String toString(Object o, TipiComponent source) {
		return "Not possible";
	}
}
