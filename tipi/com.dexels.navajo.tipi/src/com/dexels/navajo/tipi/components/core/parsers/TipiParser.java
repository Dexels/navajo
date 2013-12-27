package com.dexels.navajo.tipi.components.core.parsers;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiParser extends BaseTipiParser {
	private static final long serialVersionUID = -8448638491930265661L;

	public TipiParser() {
	}

	@Override
	public Object parse(TipiComponent source, String expression, TipiEvent event) {
		return getTipiByPath(source, expression);
	}

}
