package com.dexels.navajo.document.navascript.tags;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseNode;

public class DebugTag extends BaseNode implements NS3Compatible {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7247187908937307178L;
	
	String condition = null;
	String value = null;
	NS3Compatible parent;

	public NS3Compatible getParentTag() {
		return parent;
	}

	public void addParent(NS3Compatible p) {
		parent = p;

	}
	
	public DebugTag(Navajo n, String value) {
		super(n);
		this.value = value;
	}

	public DebugTag(NavascriptTag navascript) {
		super(navascript);
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(NS3Utils.generateIndent(indent));
		sb.append(NS3Utils.formatConditional(getCondition()));
		sb.append(NS3Keywords.DEBUG);
		sb.append("(");
		sb.append(value);
		sb.append(")");
		sb.append(NS3Constants.EOL_DELIMITER + "\n\n");
		w.write(sb.toString().getBytes());
	}

	@Override
	public void addComment(CommentBlock cb) {
		cb.addParent(this);
	}

	@Override
	public Map<String, String> getAttributes() {
		Map<String,String> attr = new HashMap<>();
		if( condition != null ) {
			attr.put(Attributes.CONDITION, condition);
		}
		attr.put(Attributes.VALUE, value);
		return attr;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		return null;
	}

	@Override
	public String getTagName() {
		return Tags.DEBUG;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


}
