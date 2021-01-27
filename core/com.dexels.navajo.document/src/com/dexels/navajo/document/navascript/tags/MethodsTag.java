package com.dexels.navajo.document.navascript.tags;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseNode;

public class MethodsTag extends BaseNode implements NS3Compatible {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9180723975859157836L;
	
	List<BaseNode> myChildren = new ArrayList<>();
	NS3Compatible parent;

	public NS3Compatible getParentTag() {
		return parent;
	}

	public void addParent(NS3Compatible p) {
		parent = p;
	}
	
	public MethodsTag(Navajo n) {
		super(n);
	}
	
	@Override
	public void formatNS3(int indent, OutputStream w) throws Exception {
		String r = NS3Utils.generateIndent(indent) + NS3Keywords.METHODS + " {\n";
		w.write(r.getBytes());
		for ( BaseNode c : getChildren() ) {
			if ( c instanceof MethodTag ) {
				((MethodTag) c).formatNS3(indent+1, w);
			}
			w.write("\n".getBytes());
		}
		w.write("}\n\n".getBytes());
	}

	@Override
	public void addComment(CommentBlock cb) {
		cb.addParent(this);
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
		return "methods";
	}

	public void addMethod(MethodTag m) {
		myChildren.add(m);
		m.addParent(this);
	}
}
