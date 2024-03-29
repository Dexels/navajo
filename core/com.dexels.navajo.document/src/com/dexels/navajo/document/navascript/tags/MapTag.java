/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
 */
package com.dexels.navajo.document.navascript.tags;

import java.io.OutputStream;
import java.util.Map;

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
	NS3Compatible parent;

	public NS3Compatible getParentTag() {
		return parent;
	}

	public void addParent(NS3Compatible p) {
		parent = p;
	}
	
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

	public MapTag(NavascriptTag n) {
		super(n);
		myScript = n;
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
		pt.addParent(this);
		return pt;
	}

	// add <field>
	public FieldTag addField(String condition, String name) {
		FieldTag pt = new FieldTag(this, condition, name);
		super.addParam(pt);
		pt.addParent(this);
		return pt;
	}

	// add <message>
	public MessageTag addMessage(String name, String type) {
		System.err.println("In addMessage for MapTag: " + name);
		MessageTag m = new MessageTag(myScript, name, type);
		super.addMessage(m);
		m.addParent(this);
		return m;
	}

	// add <block>
	public BlockTag addBlockTag(BlockTag bt) {
		super.addBlock(bt);
		bt.addParent(this);
		return bt;
	}
	
	// add <loop>
	public LoopTag addLoopTag(LoopTag bt) {
		super.addLoop(bt);
		bt.addParent(this);
		return bt;
	}
	
	// add <map>
	public MapTag addMap(String condition, String object) {
		MapTag m = new MapTag(myScript, object, condition);
		m.setParent(this);
		super.addMap(m);
		m.addParent(this);
		return m;
	}

	// add <property>
	public PropertyTag addProperty(String condition, String name, String type) {
		PropertyTag pt = new PropertyTag(myScript, name, type, null, 0, "", "out");
		super.addProperty(pt);
		pt.addParent(this);
		return pt;
	}

	// add <break/>
	public BreakTag addBreak(String condition, String id, String description) {
		BreakTag bt = new BreakTag(myScript, condition, id, description);
		super.addBreak(bt);
		bt.addParent(this);
		return bt;
	}

	// add <include/>
	public IncludeTag addInclude(String script) {
		IncludeTag it = new IncludeTag(myScript, script);
		super.addInclude(it);
		it.addParent(this);
		return it;
	}

	protected NavascriptTag getNavascript() {
		return myScript;
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws Exception {
		StringBuffer sb = new StringBuffer();
		Map<String,String> map = getAttributes();
		sb.append(NS3Utils.generateIndent(indent));
		sb.append(NS3Utils.formatConditional(map.get("condition")));
				
		if ( isOldStyleMap && ( getRefAttribute() == null || "".equals(getRefAttribute())) ) {
			sb.append( "map" + NS3Constants.PARAMETERS_START + "object:\"" + getObject() + "\" " + NS3Constants.PARAMETERS_END );
			if (!myScript.getMapChecker().isValidClass(getObject())) {
				throw new Exception("Adapter class not found: " + getObject());
			}
		} else if ( ( field == null || "".equals(field) ) && getAdapterName() != null && !"".equals(getAdapterName())) {
			if (!myScript.getMapChecker().isValidAdapter(getAdapterName())) {
				throw new Exception("Adapter not found: " + getAdapterName());
			}
			sb.append("map." + getAdapterName());
			AttributeAssignments aa = new AttributeAssignments();
			aa.addMap(map, "condition", "ref", "object");
			sb.append(aa.format(false));
		} else {
			int index = 0;
			if ( isMappedMessage() ) {
				if ( getRefAttribute().indexOf("[") == -1 ) {
					sb.append("[" + getRefAttribute() + "] ");
				} else {
					sb.append(getRefAttribute());
				}
			} else {
				if ( super.getParent() != null ) {
					MapTag parent = (MapTag) super.getParent();
					if ( !NS3Utils.checkForDeclaredField(myScript, parent, getRefAttribute())) {
						throw new Exception("Ref field not found: " + getRefAttribute() + " for adapter: " + parent.getAdapterName());
					}
				} else {
					throw new Exception("Could not find parent map for ref field: " + getRefAttribute());
				}
				sb.append("$" + getRefAttribute() + " ");
			}
			if ( getFilter() != null && !"".equals(getFilter())) {
				sb.append(NS3Constants.PARAMETERS_START);
				sb.append( ( index > 0 ? "," : "") + Attributes.FILTER+"="+getFilter());
				sb.append(NS3Constants.PARAMETERS_END);
			}
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

	@Override
	public void addComment(CommentBlock cb) {
		super.addComment(cb);
		cb.addParent(this);
	}

	public void addSynchronized(SynchronizedTag st) {
		super.addSynchronized(st);
		st.addParent(this);
	}

	public void addDebug(DebugTag dt) {
		super.addDebug(dt);
		dt.addParent(this);
	}

}
