package com.dexels.navajo.tipi.components.core;

import java.util.Map;

public interface ThreadActivityListener {
	public void threadActivity(Map<TipiThread, String> threadStateMap, TipiThread tt, String state, int queueSize);
}
