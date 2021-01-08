package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseNode;

public class SynchronizedTag extends BaseNode implements NS3Compatible {

	List<BaseNode> myChildren = new ArrayList<>();
	String context = null;
	String key = null;
	String timeout = null;
	String breakOnNoLock = null;
	
	public SynchronizedTag(Navajo n) {
		super(n);
	}
	
	public void add(NS3Compatible node) {
		myChildren.add((BaseNode) node);
	}
	
	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(NS3Utils.generateIndent(indent));
		sb.append(Tags.SYNCHRONIZED + " ");
		// Format attributes
		AttributeAssignments aa = new AttributeAssignments();
		aa.addMap(getAttributes());
		sb.append(aa.format(false));
		sb.append(" {\n");
		w.write(sb.toString().getBytes());
		// Loop over children
		for ( BaseNode n : getChildren() ) {
			if ( n instanceof NS3Compatible ) {
				((NS3Compatible) n).formatNS3(indent + 1, w);
			}
		}
		w.write((NS3Utils.generateIndent(indent) + "}\n").getBytes());
	}

	@Override
	public void addComment(CommentBlock cb) {
		myChildren.add((BaseNode) cb);
	}

	@Override
	public Map<String, String> getAttributes() {
		Map<String,String> map = new HashMap<>();
		if ( context != null && !"".equals(context)) {
			map.put(Attributes.CONTEXT, context);
		}
		if ( key != null && !"".equals(key)) {
			map.put(Attributes.KEY, key);
		}
		if ( timeout != null && !"".equals(timeout) ) {
			map.put(Attributes.TIMEOUT, timeout+"");
		}
		if (  breakOnNoLock != null && !"".equals(breakOnNoLock) ) {
			map.put(Attributes.BREAKON_NOLOCK, "true");
		}
		return map;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		return myChildren;
	}

	@Override
	public String getTagName() {
		return Tags.SYNCHRONIZED;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getBreakOnNoLock() {
		return breakOnNoLock;
	}

	public void setBreakOnNoLock(String breakOnNoLock) {
		this.breakOnNoLock = breakOnNoLock;
	}
	
}
