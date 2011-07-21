package com.dexels.navajo.tipi;

import java.io.Serializable;
import java.util.Map;

public interface TipiEventReporter extends Serializable{

	public void tipiEventReported(TipiComponent source, String type,
			Map<String, Object> event, boolean sync);

}
