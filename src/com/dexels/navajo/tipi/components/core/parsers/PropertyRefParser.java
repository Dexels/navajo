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
public class PropertyRefParser extends BaseTipiParser {
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		// return new PropertyRef(getPropertyByPath(source, expression));
		if (expression.startsWith("!")) {
			try {
				return getAttributePropertyByPath(source, expression.substring(1));
			} catch (TipiException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return getPropertyByPath(source, expression);
		}
	}

	public String toString(Object o, TipiComponent source) {
		return "Not possible";
	}

}
