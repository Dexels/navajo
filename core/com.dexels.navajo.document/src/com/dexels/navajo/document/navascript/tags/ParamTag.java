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
import com.dexels.navajo.document.base.BaseParamTagImpl;

public class ParamTag extends BaseParamTagImpl implements NS3Compatible {


	private static final long serialVersionUID = -1401899712855487877L;
	private Navajo myScript;

	public ParamTag(Navajo n) {
		super(n);
		myScript = n;
	}

	public ParamTag(Navajo n, String condition, String name) {
		super(n, condition, name);
		myScript = n;
	}

	public ParamTag addExpression(String condition, String value) {
		ExpressionTag pt = new ExpressionTag(myScript, condition, value);
		super.addExpression(pt);
		return this;
	}

	public void addMap(MapTag mt) {
		super.addMap(mt);
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		Map<String,String> map = getAttributes();
		StringBuffer sb = new StringBuffer();
		int origIndent = indent;
		if ( map.get("condition") != null && !"".equals(map.get("condition"))) {
			String conditionStr = NS3Utils.generateIndent(indent) + NS3Constants.CONDITION_IF + map.get("condition").replaceAll("&gt;", ">").replaceAll("&lt;", "<") + NS3Constants.CONDITION_THEN;
			indent = 0;
			sb.append(conditionStr);
		}
		sb.append(NS3Utils.generateIndent(indent) + NS3Keywords.VAR + " " + getName());
		// Check for attributes
		if ( map.size() > 1 ) {
			sb.append(NS3Constants.PARAMETERS_START);
		}
		int index = 1;
		int mapSize = 0;
		for ( String k : map.keySet() ) {
			if ( !"value".equals(k) && !"name".equals(k) && !"direction".equals(k) ) {
				mapSize++;
			}
		}
		for ( String k : map.keySet() ) {
			if ( !"value".equals(k) && !"name".equals(k) && !"direction".equals(k) ) {
				sb.append(k + ":" + map.get(k));
				index++;
				if ( index <= mapSize ) {
					sb.append(NS3Constants.PARAMETERS_SEP);
				}
			}
		}
		if ( map.size() > 1 ) {
			sb.append(NS3Constants.PARAMETERS_END);
		}
		
		if ( NS3Utils.hasExpressionWithConstant( this) ) {
			sb.append(":");
		} else if ( getMap() == null ) {
			sb.append("=");
		}
		
		MapTag ref = (MapTag) getMap(); // It has a mapped ref.
		if ( ref != null ) {
			sb.append(" {\n");
			w.write(sb.toString().getBytes()); // write current buffer.
			ref.formatNS3(indent+1, w);
			w.write((NS3Utils.generateIndent(indent) + "}\n").getBytes());
		} else {
			w.write(sb.toString().getBytes()); // write current buffer.
			NS3Utils.writeConditionalExpressions(origIndent, w, getChildren());
			w.write((NS3Constants.EOL_DELIMITER + "\n\n").getBytes());
		}
	}

	public void setMode(String mode) {
		super.setMode(mode);
	}

}
