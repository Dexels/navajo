/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
 */
package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseFieldTagImpl;
import com.dexels.navajo.document.base.BaseNode;

public class FieldTag extends BaseFieldTagImpl implements NS3Compatible {

	private Navajo myScript;
	private boolean oldSkool = false;

	public FieldTag(MapTag map, String condition, String name) {
		super(map.getNavajo(), name, condition);
		this.setParent(map);
		myScript = map.getNavajo();
	}

	public FieldTag(MapTag map, String condition, String name, boolean oldSkool) {
		super(map.getNavajo(), name, condition, oldSkool);
		this.setParent(map);
		myScript = map.getNavajo();
		this.oldSkool = oldSkool;
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

	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		StringBuffer sb =  new StringBuffer();
		sb.append(NS3Utils.generateIndent(indent));
		// Check for condition
		Map<String,String> map = getAttributes();
		if ( map.get("condition") != null && !"".equals(map.get("condition"))) {
			sb.append(NS3Constants.CONDITION_IF + map.get("condition") + NS3Constants.CONDITION_THEN);
		}
		if (  getChildren() == null || getChildren().size() == 0  ) { // No expressions defined, it is an operation not a "setter".
			sb.append("$"+getName());
			sb.append(NS3Constants.PARAMETERS_START);
			Map<String,String> parameters = new HashMap<>();
			for ( String k : map.keySet() ) {
				if ( !"condition".equals(k) && !"ref".equals(k) && !"object".equals(k) ) {
					parameters.put(k, NS3Constants.EXPRESSION_START + map.get(k) + NS3Constants.EXPRESSION_END);
				}
			}
			int index = 0;
			for ( String k : parameters.keySet() ) {
				index++;
				sb.append(k + "=" + parameters.get(k));
				if ( index < parameters.size() ) {
					sb.append(NS3Constants.PARAMETERS_SEP);
				}
			}
			sb.append(NS3Constants.PARAMETERS_END);
			sb.append(NS3Constants.EOL_DELIMITER + "\n");
			w.write(sb.toString().getBytes());
		} else {
			sb.append("$"+getName() + " = ");
			w.write(sb.toString().getBytes());
			if ( getChildren().size() == 1 ) {
				ExpressionTag et = (ExpressionTag) getChildren().get(0);
				et.formatNS3(0, w);
			} else {
				w.write("\n".getBytes());
				int index = 0;
				for ( BaseNode e : getChildren() ) {
					ExpressionTag et = (ExpressionTag) e;
					et.formatNS3(indent+1, w);
					index++;
					if ( index < getChildren().size() ) {
						w.write("\n".getBytes());
					}
				}
			}
			w.write((NS3Constants.EOL_DELIMITER + "\n\n").getBytes());
		}
	}

}
