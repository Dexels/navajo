package com.dexels.navajo.rhino;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.document.Property;

public class PropertyAccessMap implements Map<String, Property> {

	private final StackScriptEnvironment context;

	public PropertyAccessMap(StackScriptEnvironment n) {
		this.context = n;
	}

	public void clear() {

	}

	public boolean containsKey(Object key) {
		Property o = get(key);
		return o == null;
	}

	public boolean containsValue(Object value) {
		return false;
	}

	public Set<java.util.Map.Entry<String, Property>> entrySet() {
		return null;
	}

	public Property get(Object key) {
		String path = (String) key;
		return context.parsePropertyPath(path);
	}

	public boolean isEmpty() {
		return false;
	}

	public Set<String> keySet() {
		return null;
	}

	public Property put(String key, Property value) {
		return null;
	}

	public int size() {
		return 1;
	}

	public Collection<Property> values() {
		return null;
	}

	public void putAll(Map<? extends String, ? extends Property> m) {

	}

	public Property remove(Object key) {
		return null;
	}

}
