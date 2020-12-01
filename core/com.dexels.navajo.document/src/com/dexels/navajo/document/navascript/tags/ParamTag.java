package com.dexels.navajo.document.navascript.tags;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseParamTagImpl;

public class ParamTag extends BaseParamTagImpl {

	private Navajo myScript;
	
	public ParamTag(Navajo n, String condition, String name) {
		super(n, condition, name);
		myScript = n;
	}
	
	public ParamTag addExpression(String condition, String value) {
		ExpressionTag pt = new ExpressionTag(myScript, condition, value);
		super.addExpression(pt);
		return this;
	}
	
}
