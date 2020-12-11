/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.base.BaseNode;
import com.dexels.navajo.document.base.BaseParamTagImpl;

public class ParamTag extends BaseParamTagImpl implements NS3Compatible {

	private Navajo myScript;
	
	public ParamTag(Navajo n, String condition, String name) {
		super(n, condition, name);
		myScript = n;
	}
	
	public ParamTag addExpression(String condition, String value) {
		ExpressionTag pt = new ExpressionTag(myScript, condition, value);
		super.addExpression(pt);
		return this;
	}

	@Override
	public void writeNS3(int indent, OutputStream w) throws IOException {
		w.write((NS3Utils.generateIndent(indent) + "var " + getName() + " = ").getBytes());
		if ( getChildren().size() == 1 ) {
			ExpressionTag et = (ExpressionTag) getChildren().get(0);
			et.writeNS3(0, w);
		} else {
			w.write("\n".getBytes());
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
