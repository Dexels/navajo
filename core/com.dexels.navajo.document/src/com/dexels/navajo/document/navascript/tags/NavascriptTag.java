package com.dexels.navajo.document.navascript.tags;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.base.BaseNavascriptImpl;

public class NavascriptTag extends BaseNavascriptImpl {

	public NavascriptTag() {
		super(NavajoFactory.getInstance());
	}
	
	public MapTag addMap(String condition, String object) {
		MapTag m = new MapTag(this, object, condition);
		super.addMap(m);
		return m;
	}
	
	public MessageTag addMessage(String name, String type) {
		MessageTag m = new MessageTag(this, name, type);
		super.addMessage(m);
		return m;
	}
	
	public ParamTag addParam(String condition, String value) {
		ParamTag pt = new ParamTag(this, condition, value);
		super.addParam(pt);
		return pt;
	}
	
	public IncludeTag addInclude(String script) {
		IncludeTag it = new IncludeTag(this, script);
		super.addInclude(it);
		return it;
	}
	
	public ValidationsTag addValidations() {
		ValidationsTag vt = new ValidationsTag(this);
		super.addValidations(vt);
		return vt;
	}
	
	
}
