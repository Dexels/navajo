package com.dexels.navajo.document.navascript.tags;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseCheckTagImpl;

public class CheckTag extends BaseCheckTagImpl {

	public CheckTag(Navajo n, String code, String description, String condition) {
		super(n, code, description, null, condition);
	}
	
	public void setRule(String r) {
		super.setRule(r);
	}

}
