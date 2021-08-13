/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Check;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Validations;

public class BaseValidationsTagImpl extends BaseNode implements Validations {

	List<BaseCheckTagImpl> children = new ArrayList<>();

	public BaseValidationsTagImpl(Navajo n) {
		super(n);
	}

	public void addCheck(BaseCheckTagImpl c) {
		children.add(c);
	}

	@Override
	public Map<String, String> getAttributes() {
		return null;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		return children;
	}

	@Override
	public String getTagName() {
		return "validations";
	}

	@Override
	public List<Check> getChecks() {
		List<Check> checks = new ArrayList<>();
		for ( BaseCheckTagImpl a : children ) {
			checks.add(a);
		}
		return checks;
	}

}
