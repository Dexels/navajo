package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiTypeParser;
import com.dexels.navajo.tipi.internal.GlobalRef;
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

public class GlobalRefParser extends TipiTypeParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8010662840580860429L;

	public GlobalRefParser() {
	}

	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		return new GlobalRef(expression, source.getContext());
	}

}
