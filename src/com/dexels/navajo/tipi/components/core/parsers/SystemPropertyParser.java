package com.dexels.navajo.tipi.components.core.parsers;

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

public class SystemPropertyParser extends TipiTypeParser {

	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		String result = myContext.getSystemProperty(expression);
		return result;
		// return System.getProperty(expression);
	}

}
