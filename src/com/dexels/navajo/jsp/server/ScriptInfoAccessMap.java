package com.dexels.navajo.jsp.server;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ScriptInfoAccessMap implements Map<String, ScriptStatus> {

	
	private final NavajoServerContext context;

	public ScriptInfoAccessMap(NavajoServerContext n) {
		this.context = n;
	}
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	public boolean containsKey(Object key) {
		ScriptStatus o = get(key);
		return o==null;
	}

	public boolean containsValue(Object value) {
		return false;
	}

	public Set<java.util.Map.Entry<String, ScriptStatus>> entrySet() {
		return null;
	}

	public ScriptStatus get(Object key) {
		String path = (String)key;
		return context.getScriptInfo(path);
	}

	public boolean isEmpty() {
		return false;
	}

	public Set<String> keySet() {
		return null;
	}

	public ScriptStatus put(String key, ScriptStatus value) {
		return null;
	}



	public int size() {
		return 1;
	}

	public Collection<ScriptStatus> values() {
		return null;
	}

	public void putAll(Map<? extends String, ? extends ScriptStatus> m) {
		
	}

	public ScriptStatus remove(Object key) {
		return null;
	}


}
