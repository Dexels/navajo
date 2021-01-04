package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseNode;

public class DefinesTag extends BaseNode implements NS3Compatible {

	Navajo myNavajo;
	List<DefineTag> myDefines = new ArrayList<>();
	
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
		myDefines.add(dt);
	}

}
