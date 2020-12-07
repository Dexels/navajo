package com.dexels.navajo.document.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Include;
import com.dexels.navajo.document.Navajo;

public class BaseIncludeTagImpl extends BaseNode implements Include {

	String script;
	
	public BaseIncludeTagImpl(Navajo n, String s) {
		super(n);
		script = s;
	}
	
	@Override
	public Map<String, String> getAttributes() {
		Map<String,String> m = new HashMap<>();
		m.put("script", script);
		return m;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		return null;
	}

	@Override
	public String getTagName() {
		return "include";
	}

	@Override
	public String getScript() {
		return script;
	}

	@Override
	public void setScript(String s) {
		script = s;
	}

}
