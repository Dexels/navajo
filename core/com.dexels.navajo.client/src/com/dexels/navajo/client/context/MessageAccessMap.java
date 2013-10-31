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
	@Override
	public void clear() {
		
	}

	@Override
	public boolean containsKey(Object key) {
		Message o = get(key);
		return o==null;
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<String, Message>> entrySet() {
		return null;
	}

	@Override
	public Message get(Object key) {
		String path = (String)key;
		return context.parseMessagePath(path);
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public Set<String> keySet() {
		return null;
	}

	@Override
	public Message put(String key, Message value) {
		return null;
	}



	@Override
	public int size() {
		return 1;
	}

	@Override
	public Collection<Message> values() {
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Message> m) {
		
	}

	@Override
	public Message remove(Object key) {
		return null;
	}

}
