/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Include;
import com.dexels.navajo.document.Navajo;

public class BaseIncludeTagImpl extends BaseNode implements Include {

	String script;
	String condition;
	
	public BaseIncludeTagImpl(Navajo n, String s) {
		super(n);
		script = s;
	}
	
	public BaseIncludeTagImpl(Navajo n) {
		super(n);
	}

	@Override
	public Map<String, String> getAttributes() {
		Map<String,String> m = new HashMap<>();
		m.put("script", script);
		return m;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		return null;
	}

	@Override
	public String getTagName() {
		return "include";
	}

	@Override
	public String getScript() {
		return script;
	}

	@Override
	public void setScript(String s) {
		script = s;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
