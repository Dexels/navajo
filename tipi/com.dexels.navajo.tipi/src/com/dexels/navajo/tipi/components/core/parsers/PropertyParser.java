package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
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
public class PropertyParser extends BaseTipiParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8160207631197417713L;

	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		return getPropertyValue(source, expression);
	}

	private Object getPropertyValue(TipiComponent source, String expression) {
		if (expression.startsWith("!")) {
			try {
				return getAttributePropertyValueByPath(source,
						expression.substring(1));
			} catch (TipiException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			Property p = getPropertyByPath(source, expression);
			if (p != null) {
				return p.getTypedValue();
			}
		}
		return null;
	}

	public String toString(Object o, TipiComponent source) {
		return "Not possible";
	}

}
