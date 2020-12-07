package com.dexels.navajo.document.navascript.tags;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseValidationsTagImpl;

public class ValidationsTag extends BaseValidationsTagImpl {

	private Navajo myNavajo;
	
	public ValidationsTag(Navajo n) {
		super(n);
		myNavajo = n;
	}
	
	public CheckTag addCheck(String code, String description) {
		CheckTag ct = new CheckTag(myNavajo, code, description);
		super.addCheck(ct);
		return ct;
	}

}
