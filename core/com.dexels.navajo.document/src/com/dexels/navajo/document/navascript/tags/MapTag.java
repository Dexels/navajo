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
import com.dexels.navajo.document.base.BaseMapTagImpl;
import com.dexels.navajo.document.base.BaseNode;

public class MapTag extends BaseMapTagImpl implements NS3Compatible {

	private Navajo myScript;

	public MapTag(Navajo n, String name, String condition) {
		super(n, name, condition);
		myScript = n;
	}

	public MapTag(Navajo n, String name, String condition, boolean isOldStyleMap) {
		super(n, name, condition, isOldStyleMap);
		myScript = n;
	}

	public MapTag(Navajo n, String field, String filter, MapTag parent, boolean isOldStyleMap) {
		super(n, field, filter, parent, isOldStyleMap);
		myScript = n;
	}

	public String getAdapterName() {
		String n = getTagName();
		if ( n.split("\\.").length > 1) {
			return n.split("\\.")[1];
		} else {
			return null;
		}
	}

	// add <param>
	public ParamTag addParam(String condition, String value) {
		ParamTag pt = new ParamTag(myScript, condition, value);
		super.addParam(pt);
		return pt;
	}

	// add <field>
	public FieldTag addField(String condition, String name) {
		FieldTag pt = new FieldTag(this, condition, name);
		super.addParam(pt);
		return pt;
	}

	// add <message>
	public MessageTag addMessage(String name, String type) {
		MessageTag m = new MessageTag(myScript, name, type);
		super.addMessage(m);
		return m;
	}

	// add <map>
	public MapTag addMap(String condition, String object) {
		MapTag m = new MapTag(myScript, object, condition);
		m.setParent(this);
		super.addMap(m);
		return m;
	}

	// add <property>
	public PropertyTag addProperty(String condition, String name, String type) {
		PropertyTag pt = new PropertyTag(myScript, name, type, null, 0, "", "out");
		super.addProperty(pt);
		return pt;
	}

	// add <break/>
	public BreakTag addBreak(String condition, String id, String description) {
		BreakTag bt = new BreakTag(myScript, condition, id, description);
		super.addBreak(bt);
		return bt;
	}

	// add <include/>
	public IncludeTag addInclude(String script) {
		IncludeTag it = new IncludeTag(myScript, script);
		super.addInclude(it);
		return it;
	}

	protected Navajo getNavajo() {
		return myScript;
	}

	@Override
	public void writeNS3(int indent, OutputStream w) throws IOException {
		StringBuffer sb = new StringBuffer();
		if ( getAdapterName() != null ) {
			sb.append(NS3Utils.generateIndent(indent) + "map." + getAdapterName() + " ");
		} else {
			sb.append(NS3Utils.generateIndent(indent) + "$" + getRefAttribute() + " ");
		}
		Map<String,String> map = getAttributes();
		// key=value
		for ( String k : map.keySet() ) {
			if ( !"condition".equals(k) && !"ref".equals(k) ) {
				sb.append(k+"=" + NS3Constants.EXPRESSION_START + map.get(k) + NS3Constants.EXPRESSION_END + " ");
			}
		}
		sb.append(" {\n\n");
		w.write(sb.toString().getBytes());
		// Loop over children
		for ( BaseNode n : getChildren() ) {
			if ( n instanceof NS3Compatible ) {
				((NS3Compatible) n).writeNS3(indent + 1, w);
			}
		}
		w.write((NS3Utils.generateIndent(indent) + "}\n\n").getBytes());

	}

}
