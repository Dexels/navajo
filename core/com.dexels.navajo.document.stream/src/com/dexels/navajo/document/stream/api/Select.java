package com.dexels.navajo.document.stream.api;

public class Select {
	private final String name;
	private final String value;
	private final boolean selected;

	private Select(String name, String value, boolean selected) {
		this.name = name;
		this.value = value;
		this.selected = selected;
	}
	
	public static Select create(String name, String value, boolean selected) {
		return new Select(name,value,selected);
	}

	public String name() {
		return name;
	}

	public String value() {
		return value;
	}

	public boolean selected() {
		return selected;
	}
}
