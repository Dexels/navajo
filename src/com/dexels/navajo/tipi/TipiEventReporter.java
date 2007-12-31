package com.dexels.navajo.tipi;

import java.util.*;

public interface TipiEventReporter {

	public void tipiEventReported(TipiComponent source, String type, Map event, boolean sync);
	
}
