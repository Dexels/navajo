/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.mapping.compiler.meta;

import com.dexels.navajo.document.nanoimpl.XMLElement;

public class ParameterDefinition extends ValueDefinition {

	public ParameterDefinition(String name, String field, String required, String direction, int order, String value) {
		super(name, null, required, direction);
		this.field = field;
		this.order = order;
		this.value = value;
	}

	private String field;
	private int order;
	private String value;
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public static ParameterDefinition parseDef(XMLElement e, int order) {
		
		String name = (String) e.getAttribute("name");
		String required = (String) e.getAttribute("required");
		String field = (String) e.getAttribute("field");
		String value = (String) e.getAttribute("value");
		
		ParameterDefinition pd = new ParameterDefinition(name, field, required, "in", order, value);
		
		return pd;
	}

	public int getOrder() {
		return order;
	}

	@Override
	public String getValue() {
		return value;
	}
	
}
