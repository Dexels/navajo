package com.dexels.navajo.jsp.server;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ScriptInfoAccessMap implements Map<String, ScriptStatus> {

	
	private final NavajoServerContext context;

	public ScriptInfoAccessMap(NavajoServerContext n) {
		this.context = n;
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsKey(Object key) {
		ScriptStatus o = get(key);
		return o==null;
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<String, ScriptStatus>> entrySet() {
		return null;
	}

	@Override
	public ScriptStatus get(Object key) {
		String path = (String)key;
		return context.getScriptInfo(path);
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
	public ScriptStatus put(String key, ScriptStatus value) {
		return null;
	}



	@Override
	public int size() {
		return 1;
	}

	@Override
	public Collection<ScriptStatus> values() {
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends ScriptStatus> m) {
		
	}

	@Override
	public ScriptStatus remove(Object key) {
		return null;
	}


}
