package com.dexels.navajo.client.context;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.document.Message;

public class MessageAccessMap implements Map<String, Message> {

	
	private final NavajoContext context;

	public MessageAccessMap(NavajoContext n) {
		this.context = n;
	}
	public void clear() {
		
	}

	public boolean containsKey(Object key) {
		Message o = get(key);
		return o==null;
	}

	public boolean containsValue(Object value) {
		return false;
	}

	public Set<java.util.Map.Entry<String, Message>> entrySet() {
		return null;
	}

	public Message get(Object key) {
		String path = (String)key;
		return context.parseMessagePath(path);
	}

	public boolean isEmpty() {
		return false;
	}

	public Set<String> keySet() {
		return null;
	}

	public Message put(String key, Message value) {
		return null;
	}



	public int size() {
		return 1;
	}

	public Collection<Message> values() {
		return null;
	}

	public void putAll(Map<? extends String, ? extends Message> m) {
		
	}

	public Message remove(Object key) {
		return null;
	}

}
