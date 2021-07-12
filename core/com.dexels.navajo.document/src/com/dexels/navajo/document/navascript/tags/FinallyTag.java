package com.dexels.navajo.document.navascript.tags;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseNode;

public class FinallyTag extends BaseNode implements NS3Compatible {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4585743981082505865L;
	
	List<BaseNode> myChildren = new ArrayList<>();
	NS3Compatible parent;

	public NS3Compatible getParentTag() {
		return parent;
	}

	public void addParent(NS3Compatible p) {
		parent = p;
	}
	
	public FinallyTag(Navajo n) {
		super(n);
	}
	
	public void add(NS3Compatible node) {
		myChildren.add((BaseNode) node);
	}
	
	@Override
	public void formatNS3(int indent, OutputStream w) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(NS3Utils.generateIndent(indent));
		sb.append("finally {\n");
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
	public Map<String, String> getAttributes() {
		return null;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		return myChildren;
	}

	@Override
	public String getTagName() {
		return Tags.FINALLY;
	}
	
	@Override
	public void addComment(CommentBlock cb) {
		myChildren.add(cb);
	}

}
