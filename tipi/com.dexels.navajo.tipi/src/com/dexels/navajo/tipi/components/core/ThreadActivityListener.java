package com.dexels.navajo.tipi.components.core;

import java.io.Serializable;
import java.util.Map;

public interface ThreadActivityListener extends Serializable {
	public void threadActivity(Map<TipiThread, String> threadStateMap,
			TipiThread tt, String state, int queueSize);
}
