package com.dexels.navajo.document.navascript.tags;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BasePropertyImpl;

public class PropertyTag extends BasePropertyImpl {

	Navajo myScript;
	
	public PropertyTag(Navajo n, String name, String type, String value, int length, String desc, String direction) {
		super(n, name, type, value, length, desc, direction);
		myScript = n;
	}

	public PropertyTag addExpression(String condition, String value) {
		ExpressionTag pt = new ExpressionTag(myScript, condition, value);
		super.addExpression(pt);
		return this;
	}
	
	public SelectionTag addSelection(String name, String value, boolean selected) {
		SelectionTag st = new SelectionTag(myScript, name, value, selected);
		super.addSelection(st);
		return st;
	}
	
}
