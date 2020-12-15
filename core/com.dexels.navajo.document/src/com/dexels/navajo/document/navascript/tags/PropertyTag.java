/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
 */
package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackReader;
import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseNode;
import com.dexels.navajo.document.base.BasePropertyImpl;

public class PropertyTag extends BasePropertyImpl implements NS3Compatible {

	Navajo myScript;
	private boolean isPartOfMappedSelection = false;
	
	public boolean isPartOfMappedSelection() {
		return isPartOfMappedSelection;
	}

	public void setPartOfMappedSelection(boolean isPartOfMappedSelection) {
		this.isPartOfMappedSelection = isPartOfMappedSelection;
	}

	public PropertyTag(Navajo n, String name, String type, String value, int length, String desc, String direction) {
		super(n, name, type, value, length, desc, direction);
		myScript = n;
	}

	public PropertyTag addExpression(String condition, String value) {
		ExpressionTag pt = new ExpressionTag(myScript, condition, value);
		super.addExpression(pt);
		return this;
	}

	public SelectionTag addSelection(String name, String value, boolean selected) {
		SelectionTag st = new SelectionTag(myScript, name, value, selected);
		super.addSelection(st);
		return st;
	}

	// add <map ref=""> for mapping selection property
	public MapTag addMap(String filter, String field, MapTag refParent, boolean oldStyleMap) {
		MapTag m = new MapTag(myScript, field, filter, refParent ,oldStyleMap);
		addMap(m);
		m.setMappedSelection(true);
		return m;
	}
	
	public void addMap(MapTag mt) {
		super.addSelectionMap(mt);
		mt.setMappedSelection(true);
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		StringBuffer sb = new StringBuffer();
		Map<String,String> map = getAttributes();
		if ( map.get("condition") != null && !"".equals(map.get("condition"))) {
			sb.append(NS3Constants.CONDITION_IF + map.get("condition") + NS3Constants.CONDITION_THEN);
		}
		sb.append(NS3Utils.generateIndent(indent) + ( isPartOfMappedSelection ? "option" : NS3Keywords.PROPERTY ) + " \"" + getName() + "\" ");
		// key=value
		if ( map.size() > 1 ) {
			sb.append(NS3Constants.PARAMETERS_START);
		}
		int index = 1;
		for ( String k : map.keySet() ) {
			if ( !"value".equals(k) && !"name".equals(k) ) {
				sb.append(k + ":" + map.get(k));
				index++;
				if ( index > 1 && index <  map.keySet().size() ) {
					sb.append(NS3Constants.PARAMETERS_SEP);
				}
			}
		}
		if ( map.size() > 1 ) {
			sb.append(NS3Constants.PARAMETERS_END);
		}
		MapTag ref = null;
		for ( BaseNode b : getChildren() ) {
			if ( b instanceof MapTag ) {
				ref = (MapTag) b;
			}
		}
		if ( ref == null ) {
			sb.append(" = ");
			w.write(sb.toString().getBytes());
			NS3Utils.writeConditionalExpressions(indent, w, getChildren());
			w.write((NS3Constants.EOL_DELIMITER + "\n\n").getBytes());
		} else {
			sb.append(" {\n");
			w.write(sb.toString().getBytes()); // write current buffer.
			ref.formatNS3(indent+1, w);
			w.write((NS3Utils.generateIndent(indent) + "}\n").getBytes());
		}
	}


}
