package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.document.types.*;
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

public class BinaryParser extends TipiTypeParser {
	public BinaryParser() {
	}

	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		return new Binary(expression.getBytes());
	}

}
