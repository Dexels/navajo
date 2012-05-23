package com.dexels.navajo.dsl.expression.proposals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class AdapterMethodEntry {
	private String name;
	private boolean required;
	private String field;
	private String type;
	private final Map<String,String> paramMap = new HashMap<String,String>();
	
	public void load(XMLElement x) {
		setName(x.getStringAttribute("name"));
		setType(x.getStringAttribute("type"));
		setRequired(x.getBooleanAttribute("required", "true","false", false));
		setField(x.getStringAttribute("field"));
		List<XMLElement> params = x.getChildren();
		for (XMLElement xmlElement : params) {
			boolean auto = "automatic".equals(xmlElement.getStringAttribute("required"));
			if(auto) {
				continue;
			}
			paramMap.put(xmlElement.getStringAttribute("name"), xmlElement.getStringAttribute("type"));
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getField() {
		return field;
	}
	public void setField(String f) {
		this.field = f;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getProposal() {
		StringBuffer sb = new StringBuffer();
		sb.append(getName());
		sb.append(" ");
		for (Map.Entry<String, String> e : paramMap.entrySet()) {
			sb.append(e.getKey()+"=\""+"["+e.getValue()+"]\" ");
		}
		return sb.toString();
	}
	
}
