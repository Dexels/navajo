package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.TipiComponent;
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
public class ResourceParser extends BaseTipiParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4564316145086502257L;

	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		return source.getContext().getResourceURL(expression);
	}

}
