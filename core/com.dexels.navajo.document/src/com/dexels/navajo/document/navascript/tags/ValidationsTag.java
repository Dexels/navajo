/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.navascript.tags;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseValidationsTagImpl;

public class ValidationsTag extends BaseValidationsTagImpl {

	private Navajo myNavajo;
	
	public ValidationsTag(Navajo n) {
		super(n);
		myNavajo = n;
	}
	
	public CheckTag addCheck(String code, String description, String condition) {
		CheckTag ct = new CheckTag(myNavajo, code, description, condition);
		super.addCheck(ct);
		return ct;
	}

}
