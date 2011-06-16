package com.dexels.navajo.tipi;

import java.util.Map;

public interface TipiEventReporter {

	public void tipiEventReported(TipiComponent source, String type,
			Map<String, Object> event, boolean sync);

}
