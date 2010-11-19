package com.dexels.navajo.dsl.expression.ui.contentassist;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class AdapterValueEntry {
	private String name;
	private boolean required;
	private String direction;
	private String type;
	
	public void load(XMLElement x) {
		setName(x.getStringAttribute("name"));
		setType(x.getStringAttribute("type"));
		setRequired(x.getBooleanAttribute("required", "true","false", false));
		setDirection(x.getStringAttribute("direction"));
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
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
