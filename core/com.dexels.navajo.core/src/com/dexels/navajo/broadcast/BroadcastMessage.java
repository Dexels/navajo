package com.dexels.navajo.broadcast;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.server.Access;

public class BroadcastMessage {
	private final String message;
	private final int timeToLive;
	private final String recipientExpression;
	private final long created = System.currentTimeMillis();
	private final Set<String> sentToClientIds = new HashSet<String>();
	
	public BroadcastMessage(String message, int timeToLive, String recipientExpression) {
		this.message = message;
		this.timeToLive = timeToLive;
		this.recipientExpression = recipientExpression;
	}
	
	public String getMessage() {
		return message;
	}
	public String getRecipientExpression() {
		return recipientExpression;
	}
	public int getTimeToLive() {
		return timeToLive;
	}
	
	public boolean isExpired() {
		return System.currentTimeMillis() > created+timeToLive;
	}
	
	public boolean validRecipient(Access a) {
		return true;
	}
	
	public Map<String,String> createMap() {
		Map<String,String> m = new HashMap<String,String>();
		m.put("type", "broadcast");
		m.put("message", message);
		m.put("timeToLive", ""+timeToLive);
		return m;
	}

	public void addSentToClientId(Access a) {
		sentToClientIds.add(a.getClientToken());
	}

	public boolean hasBeenSent(Access a) {
		return sentToClientIds.contains(a.getClientToken());
	}
}
