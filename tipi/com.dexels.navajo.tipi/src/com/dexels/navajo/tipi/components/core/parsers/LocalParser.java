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

public class LocalParser extends TipiTypeParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5300397979032840987L;

	public LocalParser() {
	}

	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		TipiComponent localValueComponent = null;
		if (source != null && source.getScopeHomeComponent() != null)
		{
			localValueComponent =  source.getScopeHomeComponent();
			return localValueComponent.getLocalValue(expression);
		}
		else
		{
			return null;
		}
	}

}
