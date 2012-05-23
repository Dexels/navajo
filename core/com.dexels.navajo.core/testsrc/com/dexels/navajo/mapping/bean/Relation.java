package com.dexels.navajo.mapping.bean;

import java.util.List;

public class Relation {

	private String id;
	private String selection;
	private List<String> multiple;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSelection(String s) {
		this.selection = s;
	}

	public String getSelection() {
		return selection;
	}

	public List<String> getMultiple() {
		return multiple;
	}

	public void setMultiple(List<String> multiple) {
		this.multiple = multiple;
	}

}
