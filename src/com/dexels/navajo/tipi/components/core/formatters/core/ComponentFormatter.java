package com.dexels.navajo.tipi.components.core.formatters.core;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.components.core.TipiFormatter;

public class ComponentFormatter extends TipiFormatter {

	@Override
	public String format(Object o) {
		TipiComponent tc = (TipiComponent) o;

		return "{component://" + tc.getPath() + "}";
	}

	@Override
	public Class<?> getType() {
		return TipiComponent.class;
	}

}
