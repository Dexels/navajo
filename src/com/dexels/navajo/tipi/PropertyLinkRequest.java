package com.dexels.navajo.tipi;

import java.io.Serializable;

public class PropertyLinkRequest implements Serializable {

	private static final long serialVersionUID = -3157885651903091137L;
	private String path;
	private String aspect;
	private String attributeName;

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAspect() {
		return aspect;
	}

	public void setAspect(String aspect) {
		this.aspect = aspect;
	}

	public PropertyLinkRequest(String path, String aspect) {
		this.path = path;
		this.aspect = aspect;
	}

	public String getPropertyName() {
		return path;
	}

	public void setPropertyName(String propertyName) {
		this.path = propertyName;
	}

}
