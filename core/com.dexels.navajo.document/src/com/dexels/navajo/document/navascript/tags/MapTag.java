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
import com.dexels.navajo.document.base.BaseMapTagImpl;
import com.dexels.navajo.document.base.BaseNode;

public class MapTag extends BaseMapTagImpl implements NS3Compatible {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6950939861324563776L;

	private NavascriptTag myScript;
	private boolean isOldStyleMap = false;
	private boolean isMappedSelection = false;
	private boolean isMappedMessage = false;
	private String field = null;

	public boolean isMappedMessage() {
		return isMappedMessage;
	}

	public void setMappedMessage(boolean isMappedMessage) {
		this.isMappedMessage = isMappedMessage;
	}

	public boolean isMappedSelection() {
		return isMappedSelection;
	}

	public void setMappedSelection(boolean isMappedSelection) {
		this.isMappedSelection = isMappedSelection;
	}

	public MapTag(NavascriptTag n, String name, String condition) {
		super(n, name, condition);
		myScript = n;
	}

	public MapTag(NavascriptTag n, String name, String condition, boolean isOldStyleMap) {
		super(n, name, condition, isOldStyleMap);
		this.isOldStyleMap = isOldStyleMap;
		myScript = n;
	}

	public MapTag(NavascriptTag n, String field, String filter, MapTag parent, boolean isOldStyleMap) {
		super(n, field, filter, parent, isOldStyleMap);
		this.isOldStyleMap = isOldStyleMap;
		this.field = field;
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

	protected NavascriptTag getNavascript() {
		return myScript;
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		Map<String,String> map = getAttributes();
		if ( map.get("condition") != null && !"".equals(map.get("condition"))) {
			sb.append(NS3Utils.generateIndent(indent) + NS3Constants.CONDITION_IF + map.get("condition").replaceAll("&gt;", ">").replaceAll("&lt;", "<") + NS3Constants.CONDITION_THEN);
		}
		if ( isOldStyleMap && ( getRefAttribute() == null || "".equals(getRefAttribute())) ) {
			sb.append(NS3Utils.generateIndent(indent) + "map" + NS3Constants.PARAMETERS_START + "object=\"" + getObject() + "\" " + NS3Constants.PARAMETERS_END );
		} else if ( ( field == null || "".equals(field) ) && getAdapterName() != null && !"".equals(getAdapterName())) {
			sb.append(NS3Utils.generateIndent(indent) + "map." + getAdapterName());

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
		} else {
			int index = 0;
			if ( isMappedMessage() ) {
				sb.append(NS3Utils.generateIndent(indent));
				if ( getRefAttribute().indexOf("[") == -1 ) {
					sb.append("[" + getRefAttribute() + "] ");
				} else {
					sb.append(getRefAttribute());
				}
				sb.append(NS3Constants.PARAMETERS_START);
			} else {
				sb.append(NS3Utils.generateIndent(indent) + "$" + getRefAttribute() + " ");
				sb.append(NS3Constants.PARAMETERS_START);
			}
			if ( getFilter() != null && !"".equals(getFilter())) {
				sb.append( ( index > 0 ? "," : "") + Attributes.FILTER+"="+getFilter());
			}
			sb.append(NS3Constants.PARAMETERS_END);
		}

		if ( isMappedSelection) {
			sb.append(" {\n");
		} else {
			sb.append(" {\n\n");
		}
		w.write(sb.toString().getBytes());
		// Loop over children
		for ( BaseNode n : getChildren() ) {
			if ( n instanceof NS3Compatible ) {
				if ( n instanceof PropertyTag && isMappedSelection ) {
					((PropertyTag) n).setPartOfMappedSelection(true);
				}
				((NS3Compatible) n).formatNS3(indent + 1, w);
			}
		}
		w.write((NS3Utils.generateIndent(indent) + "}\n\n").getBytes());

	}


}
