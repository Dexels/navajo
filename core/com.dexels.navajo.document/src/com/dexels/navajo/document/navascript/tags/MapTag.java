package com.dexels.navajo.document.navascript.tags;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseMapTagImpl;

public class MapTag extends BaseMapTagImpl {

	private Navajo myScript;
	
	public MapTag(Navajo n, String name, String condition) {
		super(n, name, condition);
		myScript = n;
	}
	
	public MapTag(Navajo n, String field, String filter, MapTag parent) {
		super(n, field, filter, parent);
		myScript = n;
	}
	
	// add <param>
	public ParamTag addParam(String condition, String value) {
		ParamTag pt = new ParamTag(myScript, condition, value);
		super.addParam(pt);
		return pt;
	}
	
	// add <field>
	public FieldTag addField(String condition, String name) {
		FieldTag pt = new FieldTag(this, condition, name);
		super.addParam(pt);
		return pt;
	}
	
	// add <message>
	public MessageTag addMessage(String name, String type) {
		MessageTag m = new MessageTag(myScript, name, type);
		super.addMessage(m);
		return m;
	}
	
	// add <property>
	public PropertyTag addProperty(String condition, String name, String type) {
		PropertyTag pt = new PropertyTag(myScript, name, type, null, 0, "", "out");
		super.addProperty(pt);
		return pt;
	}
	
	protected Navajo getNavajo() {
		return myScript;
	}
}
