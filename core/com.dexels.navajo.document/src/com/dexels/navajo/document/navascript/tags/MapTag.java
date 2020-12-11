/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.navascript.tags;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseMapTagImpl;

public class MapTag extends BaseMapTagImpl {

	private Navajo myScript;

	public MapTag(Navajo n, String name, String condition) {
		super(n, name, condition);
		myScript = n;
	}

	public MapTag(Navajo n, String name, String condition, boolean isOldStyleMap) {
		super(n, name, condition, isOldStyleMap);
		myScript = n;
	}

	public MapTag(Navajo n, String field, String filter, MapTag parent, boolean isOldStyleMap) {
		super(n, field, filter, parent, isOldStyleMap);
		myScript = n;
	}

	public String getAdapterName() {
		String n = getTagName();
		if ( n.split("\\.").length > 1) {
			return n.split("\\.")[1];
		} else {
			return null;
		}
	}
	
	// add <param>
	public ParamTag addParam(String condition, String value) {
		ParamTag pt = new ParamTag(myScript, condition, value);
		super.addParam(pt);
		return pt;
	}

	// add <field>
	public FieldTag addField(String condition, String name) {
		FieldTag pt = new FieldTag(this, condition, name);
		super.addParam(pt);
		return pt;
	}

	// add <message>
	public MessageTag addMessage(String name, String type) {
		MessageTag m = new MessageTag(myScript, name, type);
		super.addMessage(m);
		return m;
	}

	// add <map>
	public MapTag addMap(String condition, String object) {
		MapTag m = new MapTag(myScript, object, condition);
		m.setParent(this);
		super.addMap(m);
		return m;
	}

	// add <property>
	public PropertyTag addProperty(String condition, String name, String type) {
		PropertyTag pt = new PropertyTag(myScript, name, type, null, 0, "", "out");
		super.addProperty(pt);
		return pt;
	}

	protected Navajo getNavajo() {
		return myScript;
	}

}
