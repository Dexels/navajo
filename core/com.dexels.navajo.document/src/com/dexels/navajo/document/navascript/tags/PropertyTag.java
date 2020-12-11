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

	Navajo myScript;
	
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

	@Override
	public void writeNS3(int indent, OutputStream w) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(NS3Utils.generateIndent(indent) + "property " + getName() + " ");
		Map<String,String> map = getAttributes();
		// key=value
		for ( String k : map.keySet() ) {
			if ( !"value".equals(k) && !"name".equals(k) ) {
				sb.append(k+"=\""+map.get(k) + "\" ");
			}
		}
		sb.append("= ");
		w.write(sb.toString().getBytes());
		if ( getChildren().size() == 1 ) {
			ExpressionTag et = (ExpressionTag) getChildren().get(0);
			et.writeNS3(0, w);
		} else {
			w.write("\n".getBytes());
			int size = getChildren().size();
			int index = 0;
			for ( BaseNode e : getChildren() ) {
				ExpressionTag et = (ExpressionTag) e;
				et.writeNS3(indent+1, w);
				index++;
				if ( index < getChildren().size() ) {
					w.write("\n".getBytes());
				}
			}
		}
		w.write((NS3Constants.EOL_DELIMITER + "\n\n").getBytes());
	}
	
}
