package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseNode;

public class DefinesTag extends BaseNode implements NS3Compatible {

	/**
	 * 
	 */
	private static final long serialVersionUID = 198025834121652631L;
	
	Navajo myNavajo;
	List<DefineTag> myDefines = new ArrayList<>();
	NS3Compatible parent;

	public NS3Compatible getParentTag() {
		return parent;
	}

	public void addParent(NS3Compatible p) {
		parent = p;

	}
	
	public DefinesTag(Navajo n) {
		myNavajo = n;
	}
	
	@Override
	public Map<String, String> getAttributes() {
		return null;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		return myDefines;
	}

	@Override
	public String getTagName() {
		return Tags.DEFINES;
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		for ( DefineTag dt : myDefines ) {
			dt.formatNS3(indent, w);
		}
	}

	public void addDefine(DefineTag dt) {
		dt.addParent(this);
		myDefines.add(dt);
	}

	@Override
	public void addComment(CommentBlock cb) {
		cb.addParent(this);
	}
}
