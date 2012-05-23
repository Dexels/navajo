package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiValue;
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

public class EventParser extends BaseTipiParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = -341180924518153889L;

	public EventParser() {
	}

	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		if (event != null) {
			TipiValue o = event.getEventParameter(expression);
			if (o == null) {
				return null;
			}
			if (o.getRawValue() != null) {
			}
			return o.getRawValue();
		}
		return null;
	}

}
