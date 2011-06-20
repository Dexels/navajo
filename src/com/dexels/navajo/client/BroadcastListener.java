package com.dexels.navajo.client;

import java.util.Map;

public interface BroadcastListener {
	public void broadcast(String message, Map<String,String> params);
}
