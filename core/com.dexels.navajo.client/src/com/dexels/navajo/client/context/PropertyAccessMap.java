package com.dexels.navajo.client.context;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.document.Property;

public class PropertyAccessMap implements Map<String, Property> {

	
	private final NavajoContext context;

	public PropertyAccessMap(NavajoContext n) {
		this.context = n;
	}
	@Override
	public void clear() {
		
	}

	@Override
	public boolean containsKey(Object key) {
		Property o = get(key);
		return o==null;
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<String, Property>> entrySet() {
		return null;
	}

	@Override
	public Property get(Object key) {
		String path = (String)key;
		return context.parsePropertyPath(path);
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
	public Property put(String key, Property value) {
		return null;
	}



	@Override
	public int size() {
		return 1;
	}

	@Override
	public Collection<Property> values() {
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Property> m) {
		
	}

	@Override
	public Property remove(Object key) {
		return null;
	}

}
