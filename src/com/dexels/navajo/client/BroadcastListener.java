package com.dexels.navajo.client;

import java.util.*;

public interface BroadcastListener {
	public void broadcast(String message, Map<String,String> params);
}
