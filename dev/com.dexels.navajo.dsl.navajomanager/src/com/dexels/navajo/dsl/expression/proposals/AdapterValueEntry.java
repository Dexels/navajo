package com.dexels.navajo.dsl.expression.proposals;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class AdapterValueEntry {
	private String name;
	private boolean required;
	private String direction;
	private String type;
	private String map;


	public void load(XMLElement x) {
		setName(x.getStringAttribute("name"));
		setType(x.getStringAttribute("type"));
		setRequired(x.getBooleanAttribute("required", "true","false", false));
		setDirection(x.getStringAttribute("direction"));
		setMap(x.getStringAttribute("map"));
	}
	
	
	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
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
