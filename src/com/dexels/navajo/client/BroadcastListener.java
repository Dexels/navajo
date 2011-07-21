package com.dexels.navajo.client;

import java.io.Serializable;
import java.util.Map;

public interface BroadcastListener extends Serializable{
	public void broadcast(String message, Map<String,String> params);
}
