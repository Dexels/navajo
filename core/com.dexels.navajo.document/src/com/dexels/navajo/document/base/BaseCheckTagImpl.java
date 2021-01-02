/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.base;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Check;
import com.dexels.navajo.document.Navajo;

public class BaseCheckTagImpl extends BaseNode implements Check {

	String code;
	String description;
	String rule;
	String condition;

	public BaseCheckTagImpl(Navajo n, String code, String description, String rule, String condition) {
		super(n);
		this.code = code;
		this.description = description;
		this.rule = rule;
		this.condition = condition;
	}

	public BaseCheckTagImpl(Navajo n) {
		super(n);
	}

	@Override
	public Map<String, String> getAttributes() {
		Map<String,String> map = new HashMap<>();
		map.put("code", code);
		if ( description != null ) {
			map.put("description", description);
		}
		if ( condition != null ) {
			map.put("condition", condition);
		}
		return map;

	}

	@Override
	public List<? extends BaseNode> getChildren() {
		return null;
	}

	@Override
	public String getTagName() {
		return "check";
	}

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String s) {
		code = s;
	}
	
	public void setCondition(String c) {
		condition = c;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String s) {
		description = s;
	}
	
	@Override
	public String getRule() {
		return rule;
	}

	@Override
	public boolean hasTextNode() {
		return true;
	}

	@Override
	public void writeText(Writer w) throws IOException  {
		if ( rule != null ) {
			w.write(rule);
		}
	}

	@Override
	public void setRule(String r) {
		rule = r;
	}

	@Override
	public String getCondition() {
		return condition;
	}

}
