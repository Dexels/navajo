package com.dexels.navajo.document.base;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Check;
import com.dexels.navajo.document.Navajo;

public class BaseCheckTagImpl extends BaseNode implements Check {

	String code;
	String description;
	String rule;

	public BaseCheckTagImpl(Navajo n, String code, String description, String rule) {
		super(n);
		this.code = code;
		this.description = description;
		this.rule = rule;
	}

	@Override
	public Map<String, String> getAttributes() {
		Map<String,String> map = new HashMap<>();
		map.put("code", code);
		if ( description != null ) {
			map.put("description", description);
		}
		return map;

	}

	@Override
	public List<? extends BaseNode> getChildren() {
		return null;
	}

	@Override
	public String getTagName() {
		return "check";
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getRule() {
		return rule;
	}

	@Override
	public boolean hasTextNode() {
		return true;
	}

	@Override
	public void writeText(Writer w) throws IOException  {
		if ( rule != null ) {
			w.write(rule);
		}
	}

	@Override
	public void setRule(String r) {
		rule = r;
	}

}
