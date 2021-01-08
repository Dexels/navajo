/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
 */
package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseNode;
import com.dexels.navajo.document.base.BasePropertyImpl;

public class PropertyTag extends BasePropertyImpl implements NS3Compatible {


	private static final long serialVersionUID = 7993747297861067599L;

	NavascriptTag myScript;
	private boolean isPartOfMappedSelection = false;

	public boolean isPartOfMappedSelection() {
		return isPartOfMappedSelection;
	}

	public void setPartOfMappedSelection(boolean isPartOfMappedSelection) {
		this.isPartOfMappedSelection = isPartOfMappedSelection;
	}

	public PropertyTag(NavascriptTag n, String name, String type, String value, int length, String desc, String direction) {
		super(n, name, type, value, length, desc, direction);
		myScript = n;
	}

	public PropertyTag(NavascriptTag n) {
		super(n);
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

	public String getCondition() {
		return condition;
	}

	public void setCondition(String s) {
		condition = s;
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		StringBuffer sb = new StringBuffer();
		Map<String,String> map = getAttributes();
		String propertyName = getName();
		String value = map.get("value");

		if ( condition != null && !"".equals(condition)) {
			sb.append(NS3Utils.generateIndent(indent));
			sb.append(NS3Constants.CONDITION_IF + condition.replaceAll("&gt;", ">").replaceAll("&lt;", "<") + NS3Constants.CONDITION_THEN);
		}
		sb.append(NS3Utils.generateIndent(indent) + ( isPartOfMappedSelection ?  NS3Keywords.OPTION + " " + getName(): NS3Keywords.PROPERTY + " \"" + propertyName + "\"") );

		// Format attributes
		AttributeAssignments aa = new AttributeAssignments();
		aa.addMap(map, "condition", "value", "name");
		if ( map.get("description") != null ) {
			aa.add("description", NS3Constants.DOUBLE_QUOTE + map.get("description") + NS3Constants.DOUBLE_QUOTE);
		}
		sb.append(" ");
		sb.append(aa.format(true));

		MapTag ref = null;
		for ( BaseNode b : getChildren() ) {
			if ( b instanceof MapTag ) {
				ref = (MapTag) b;
			}
		}
		if ( getChildren().size() > 0 && getChildren().get(0) instanceof SelectionTag) {
			sb.append(" {\n");
			w.write(sb.toString().getBytes());
			for ( BaseNode b : getChildren() ) {
				((NS3Compatible) b).formatNS3(indent+1, w);
			}
			w.write((NS3Utils.generateIndent(indent) + "}\n").getBytes());
			return;
		} else if ( value != null ) {
			sb.append(" = ");
			sb.append(NS3Constants.DOUBLE_QUOTE + value + NS3Constants.DOUBLE_QUOTE + NS3Constants.EOL_DELIMITER + "\n");
			w.write(sb.toString().getBytes());
		} else if ( ref == null ) {
			if (  NS3Utils.hasExpressionWithConstant( this)  ) {
				sb.append(" = "); // It is a string literal in the expression.
			} else if ( getChildren().size() >= 1) {
				sb.append(" = ");
			}
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

	@Override
	public void addComment(CommentBlock cb) {

	}

}
