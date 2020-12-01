package com.dexels.navajo.document.navascript.tags;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseMessageTagImpl;

public class MessageTag extends BaseMessageTagImpl {

	private Navajo myScript;
	
	public MessageTag(Navajo n, String name, String type) {
		super(n, name);
		if ( type != null ) {
			super.setType(type);
		}
		myScript = n;
	}
	
	public ParamTag addParam(String condition, String value) {
		ParamTag pt = new ParamTag(myScript, condition, value);
		super.addParam(pt);
		return pt;
	}
	
	public PropertyTag addProperty(String condition, String name, String type, String value, int length, String description, String dir) {
		PropertyTag pt = new PropertyTag(myScript, name, type, value, length, description, dir);
		super.addProperty(pt);
		return pt;
	}
	
	public PropertyTag addProperty(String condition, String name, String type, String value) {
		PropertyTag pt = new PropertyTag(myScript, name, type, value, 0, "", "out");
		super.addProperty(pt);
		return pt;
	}
	
	public PropertyTag addProperty(String condition, String name, String type) {
		PropertyTag pt = new PropertyTag(myScript, name, type, null, 0, "", "out");
		super.addProperty(pt);
		return pt;
	}
	
	public MapTag addMap(String filter, String field, MapTag refParent) {
		MapTag m = new MapTag(myScript, field, filter, refParent);
		super.addMap(m);
		return m;
	}
	
	protected Navajo getNavajo() {
		return myScript;
	}
}
