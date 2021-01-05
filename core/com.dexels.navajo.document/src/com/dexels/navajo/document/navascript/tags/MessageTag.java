/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
 */
package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackReader;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseMessageTagImpl;
import com.dexels.navajo.document.base.BaseNode;

public class MessageTag extends BaseMessageTagImpl implements NS3Compatible {

	private NavascriptTag myScript;
	private int ignoredMessageLevel = 0;

	public MessageTag(NavascriptTag n) {
		super(n);
		myScript = n;
	}

	public MessageTag(NavascriptTag n, String name, String type) {
		super(n, name);
		if ( type != null ) {
			super.setType(type);
		}
		myScript = n;
	}

	// add <param>
	public ParamTag addParam(String condition, String value) {
		ParamTag pt = new ParamTag(myScript, condition, value);
		super.addParam(pt);
		return pt;
	}

	public FieldTag addField(MapTag parent, String condition, String name) {
		FieldTag pt = new FieldTag(parent, condition, name);
		super.addField(pt);
		return pt;
	}

	public PropertyTag addProperty(String condition, String name, String type, String value, int length, String description, String dir) {
		PropertyTag pt = new PropertyTag(myScript, name, type, value, length, description, dir);
		super.addProperty(pt);
		return pt;
	}

	public PropertyTag addProperty(String condition, String name, String type, String value) {
		PropertyTag pt = new PropertyTag(myScript, name, type, value, 0, "", "out");
		super.addProperty(pt);
		return pt;
	}

	// add <property>
	public PropertyTag addProperty(String condition, String name, String type) {
		PropertyTag pt = new PropertyTag(myScript, name, type, null, 0, "", "out");
		super.addProperty(pt);
		return pt;
	}

	// add <map>
	public MapTag addMap(String filter, String field, MapTag refParent, boolean oldStyleMap) {
		MapTag m = new MapTag(myScript, field, filter, refParent ,oldStyleMap);
		super.addMap(m);
		return m;
	}

	// add <message>
	public MessageTag addMessage(String name, String type) {
		MessageTag m = new MessageTag(myScript, name, type);
		super.addMessage(m);
		return m;
	}

	// add <include>
	public IncludeTag addInclude(String script) {
		IncludeTag it = new IncludeTag(myScript, script);
		super.addInclude(it);
		return it;
	}

	// add <break/>
	public BreakTag addBreak(String condition, String id, String description) {
		BreakTag bt = new BreakTag(myScript, condition, id, description);
		super.addBreak(bt);
		return bt;
	}

	// add <block/>
	public BlockTag addBlockTag(BlockTag bt) {
		super.addBlock(bt);
		return bt;
	}

	protected Navajo getNavajo() {
		return myScript;
	}

	private boolean hasParameters() {
		return  ( getOrderBy() != null && !"".equals(getOrderBy() )) || ( getMode() != null && !"".equals(getMode())) || ( getType() != null && !"simple".equals(getType()) && !"".equals(getType()));
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		int size = getChildren().size();
		String msgName = NS3Utils.removeParentAddressing(ignoredMessageLevel, getName());
		Map<String,String> map = getAttributes();
		if ( map.get("condition") != null && !"".equals(map.get("condition"))) {
			String conditionStr = NS3Utils.generateIndent(indent) + NS3Constants.CONDITION_IF + map.get("condition").replaceAll("&gt;", ">").replaceAll("&lt;", "<") + NS3Constants.CONDITION_THEN;
			w.write(conditionStr.getBytes());
			String start = ( isAntiMessage() ? "anti" : "") + "message \"" + msgName + "\" "; 
			w.write(start.getBytes());
		} else {
			String start = NS3Utils.generateIndent(indent) + ( isAntiMessage() ? "anti" : "") + "message \"" + msgName + "\" "; 
			w.write(start.getBytes());
		}
		int index = 0;
		if ( hasParameters() ) {
			w.write(NS3Constants.PARAMETERS_START.getBytes());
		}
		if ( getOrderBy() != null && !"".equals(getOrderBy())) {
			String ob = "orderby=" + getOrderBy() + " ";
			w.write(ob.getBytes());
			index++;
		}
		if ( getMode() != null && !"".equals(getMode())) {
			if ( index > 0 ) {
				w.write(",".getBytes());
			}
			String ob = "mode:"+getMode().replaceAll("_", "");
			w.write(ob.getBytes());
			index++;
		}
		if ( getType() != null && !"simple".equals(getType()) && !"".equals(getType())) {
			if ( index > 0 ) {
				w.write(",".getBytes());
			}
			String ob = "type:"+getType();
			w.write(ob.getBytes());
			index++;
		}
		if ( index > 0 ) {
			w.write(NS3Constants.PARAMETERS_END.getBytes());
		}
		if ( size > 0 ) {
			String openBlock = " {\n\n";
			w.write(openBlock.getBytes());
		} else {
			w.write((NS3Constants.EOL_DELIMITER + "\n").getBytes());
		}
		// Loop over children
		for ( BaseNode n : getChildren() ) {
			if ( n instanceof NS3Compatible ) {
				((NS3Compatible) n).formatNS3(indent + 1, w);
			}
		}
		if ( size > 0 ) {
			String end = NS3Utils.generateIndent(indent) + "}\n\n";
			w.write(end.getBytes());
		}

	}
	
	@Override
	public void addComment(CommentBlock cb) {
		super.addComment(cb);
	}

}
