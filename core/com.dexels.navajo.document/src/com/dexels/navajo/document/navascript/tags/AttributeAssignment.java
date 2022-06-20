package com.dexels.navajo.document.navascript.tags;

public class AttributeAssignment  {

	private String attribName;
	private String attribValue;

	public AttributeAssignment(String name, String value) {
		this.attribName = name;
		this.attribValue = value;
	}

	public String getAttribName() {
		return attribName;
	}
	
	public String getAttribValue() {
		return attribValue;
	}
	
	@Override
	public boolean equals(Object o) {
		return ((AttributeAssignment) o).getAttribName().equals(getAttribName());
	}
	
	@Override
	public int hashCode() {
		return attribName.hashCode();
	}

}
