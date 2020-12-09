/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.navascript.tags;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseFieldTagImpl;

public class FieldTag extends BaseFieldTagImpl {

	private Navajo myScript;

	public FieldTag(MapTag map, String condition, String name) {
		super(map.getNavajo(), name, condition);
		this.setParent(map);
		myScript = map.getNavajo();
	}

	public FieldTag(MapTag map, String condition, String name, boolean oldSkool) {
		super(map.getNavajo(), name, condition, oldSkool);
		this.setParent(map);
		myScript = map.getNavajo();
	}

	public FieldTag addExpression(String condition, String value) {
		ExpressionTag pt = new ExpressionTag(myScript, condition, value);
		super.addExpression(pt);
		return this;
	}

	// add <map>
	public MapTag addMap(String filter, String field, MapTag refParent, boolean oldStyleMap) {
		MapTag m = new MapTag(myScript, field, filter, refParent, oldStyleMap);
		super.addMap(m);
		return m;
	}

}
