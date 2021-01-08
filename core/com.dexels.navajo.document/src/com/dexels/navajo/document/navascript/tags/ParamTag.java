/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
 */
package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import com.dexels.navajo.document.Message;
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

	public void addParamTag(ParamTag pt) throws Exception {
		if ( pt.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT) && getType().equals(Message.MSG_TYPE_ARRAY)) {
			super.addParam(pt);
		} else if ( getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT) && ( pt.getType() == null || !"".equals(getType()) ) ) {
			super.addParam(pt);
		} else {
			throw new Exception("This param type: " + pt.getType() + " is no expected under a param of type " + getType());
		}


	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		Map<String,String> map = getAttributes();

		boolean isArrayElement = ( getType() != null && getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT));
		boolean hasArrayElements = ( getChildren().size() > 0 && getChildren().get(0) instanceof ParamTag 
				&&  Message.MSG_TYPE_ARRAY_ELEMENT.equals(((ParamTag) getChildren().get(0)).getType()));

		boolean hasSubParam = ( getChildren().size() > 0 && getChildren().get(0) instanceof ParamTag 
				&&  !Message.MSG_TYPE_ARRAY_ELEMENT.equals(((ParamTag) getChildren().get(0)).getType()));

		//System.err.println(getName() + ", isArrayElement: " + isArrayElement + ", hasArrayElements: " + hasArrayElements + ", hasSubParam: " + hasSubParam);

		StringBuffer sb = new StringBuffer();
		int origIndent = indent;
		if ( map.get("condition") != null && !"".equals(map.get("condition"))) {
			String conditionStr = NS3Utils.generateIndent(indent) + NS3Constants.CONDITION_IF + map.get("condition").replaceAll("&gt;", ">").replaceAll("&lt;", "<") + NS3Constants.CONDITION_THEN;
			indent = 0;
			sb.append(conditionStr);
		}
		if ( !isArrayElement  ) {
			sb.append(NS3Utils.generateIndent(indent) + NS3Keywords.VAR + " " + getName());
		}
		// Check for attributes
		if ( !isArrayElement ) {
			AttributeAssignments aa = new AttributeAssignments();
			aa.addMap(getAttributes(), "name", "condition", ( hasArrayElements || "simple".equals(getType()) ? "type" : ""));
			sb.append(" ");
			sb.append(aa.format(true));
		}

		boolean hasParam = hasParamChildren(); // It has a param array element
		MapTag ref = (MapTag) getMap(); // It has a mapped ref.

		if ( NS3Utils.hasExpressionWithConstant( this) ) {
			sb.append(" = ");
		} else if ( !hasParam && ref == null ) {
			sb.append(" = ");
		}

		if ( ref != null ) {
			sb.append(" {\n");
			w.write(sb.toString().getBytes()); // write current buffer.
			ref.formatNS3(indent+1, w);
			w.write((NS3Utils.generateIndent(indent) + "}\n").getBytes());
		} else if (hasArrayElements) {
			sb.append(" [\n");
			w.write(sb.toString().getBytes()); // write current buffer.
			int size = getChildren().size();
			int count = 0;
			for ( BaseNode p : getChildren() ) {
				((NS3Compatible) p).formatNS3(indent+1, w);
				if ( hasArrayElements && count < size - 1 ) {
					w.write(",".getBytes());
				}
				count++;
			}
			w.write( ("\n" + NS3Utils.generateIndent(indent) + "]\n").getBytes());
		} else if ( isArrayElement || hasSubParam ) {
			if ( isArrayElement ) {
				sb.append("\n" + NS3Utils.generateIndent(indent) + "{\n" ) ;
			} else {
				sb.append(" {\n" ) ;
			}
			w.write(sb.toString().getBytes()); // write current buffer.
			for ( BaseNode p : getChildren() ) {
				((NS3Compatible) p).formatNS3(indent+1, w);
			}
			w.write((NS3Utils.generateIndent(indent) + "}").getBytes()) ;
			if ( hasSubParam && !isArrayElement ) {
				w.write("\n".getBytes());
			}
		} else {
			w.write(sb.toString().getBytes()); // write current buffer.
			NS3Utils.writeConditionalExpressions(origIndent, w, getChildren());
			w.write((NS3Constants.EOL_DELIMITER + "\n\n").getBytes());
		}
	}

	public void setMode(String mode) {
		super.setMode(mode);
	}

	@Override
	public void addComment(CommentBlock cb) {

	}

}
