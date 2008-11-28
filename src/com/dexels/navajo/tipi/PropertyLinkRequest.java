package com.dexels.navajo.tipi;

import java.beans.*;
import java.util.*;

import com.dexels.navajo.document.*;

public class PropertyLinkRequest  {
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
