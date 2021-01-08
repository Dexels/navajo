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

	private NavascriptTag myScript;
	private boolean oldSkool = false;
	private boolean isSetter = false;

	public FieldTag(MapTag map, String condition, String name) {
		super(map.getNavascript(), name, condition);
		this.setParent(map);
		myScript = map.getNavascript();
	}

	public FieldTag(MapTag map, String condition, String name, boolean oldSkool) {
		super(map.getNavascript(), name, condition, oldSkool);
		this.setParent(map);
		myScript = map.getNavascript();
		this.oldSkool = oldSkool;
	}

	public FieldTag(MapTag map) {
		super(map.getNavascript());
		myScript = map.getNavascript();
	}

	public FieldTag addExpression(String condition, String value) {
		ExpressionTag pt = new ExpressionTag(myScript, condition, value);
		super.addExpression(pt);
		return this;
	}

	// add <map>
	public MapTag addMap(String filter, String field, MapTag refParent, boolean oldStyleMap) {
		System.err.println("adding map " + field + " to " + getName());
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
		String adapterName = ((MapTag) getParent()).getAdapterName();
		sb.append(NS3Utils.formatConditional(map.get("condition")));
		
		if (  ( getChildren() == null || getChildren().size() == 0 ) && getConstant() == null && !isSetter && !myScript.getMapChecker().isField(adapterName, getName())  ) { // No expressions defined, it is an "operation" not a "setter".
			sb.append(NS3Constants.ADAPTER_OPERATION + getName());
			AttributeAssignments aa = new AttributeAssignments();
			aa.addMap(map, "condition", "ref", "object");
			sb.append(aa.format(false));
			sb.append(NS3Constants.EOL_DELIMITER + "\n");
			w.write(sb.toString().getBytes());
		} else if ( getChildren() != null && getChildren().get(0) instanceof MapTag ) { // <map ref=<array message> >

			sb.append("$"+getName());
			sb.append(" {\n");
			w.write(sb.toString().getBytes());
			((MapTag) getChildren().get(0)).setMappedMessage(true); // Mark map as a mapped message
			((MapTag) getChildren().get(0)).formatNS3(indent+1,w);

			w.write((NS3Utils.generateIndent(indent) + "}\n").getBytes());

		} else { // standard "setter" field
			if ( getConstant() != null ) { // setter with a constant string literal
				sb.append("$"+getName() + " = ");
				sb.append(NS3Utils.formatStringLiteral(getConstant()));
				w.write(sb.toString().getBytes());
			} else if ( getChildren() == null ) { // it must have a value attribute.
				sb.append("$"+getName() + " = ");
				sb.append(map.get("value"));
				w.write(sb.toString().getBytes());
			} else { // setter with normal expression(s)
				sb.append("$"+getName());
				w.write(sb.toString().getBytes());
				if ( getChildren().size() == 1 ) {
					ExpressionTag et = (ExpressionTag) getChildren().get(0);
					if ( et.getConstant() != null ) {
						w.write("=".getBytes());
					} else {
						w.write("=".getBytes());
					}
					et.formatNS3(0, w);
				} else {
					w.write("=\n".getBytes());
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
			}
			w.write((NS3Constants.EOL_DELIMITER + "\n\n").getBytes());
		}
	}

	
	public boolean isSetter() {
		return isSetter;
	}

	public void setSetter(boolean isSetter) {
		this.isSetter = isSetter;
	}

	@Override
	public void addComment(CommentBlock cb) {
		
	}
}
