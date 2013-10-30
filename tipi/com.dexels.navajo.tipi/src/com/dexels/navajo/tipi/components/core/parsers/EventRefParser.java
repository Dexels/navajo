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

public class EventRefParser extends BaseTipiParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 292630508265350326L;

	public EventRefParser() {
	}

	@Override
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		if (event != null) {
			EventRef er = new EventRef(expression, event);
			return er;
		}
		return null;
	}

}
