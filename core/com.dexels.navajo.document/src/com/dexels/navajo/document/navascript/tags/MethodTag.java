package com.dexels.navajo.document.navascript.tags;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseNode;

public class MethodTag extends BaseNode implements NS3Compatible {

	String scriptName;
	NS3Compatible parent;

	public NS3Compatible getParentTag() {
		return parent;
	}

	public void addParent(NS3Compatible p) {
		parent = p;
	}
	
	public MethodTag(Navajo n) {
		super(n);
		
	}
	@Override
	public void formatNS3(int indent, OutputStream w) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(NS3Utils.generateIndent(indent));
		sb.append(NS3Constants.DOUBLE_QUOTE);
		sb.append(scriptName);
		sb.append(NS3Constants.DOUBLE_QUOTE);
		sb.append(NS3Constants.EOL_DELIMITER);
		w.write(sb.toString().getBytes());
	}

	@Override
	public void addComment(CommentBlock cb) {
		cb.addParent(this);
	}

	@Override
	public Map<String, String> getAttributes() {
		Map<String,String> m = new HashMap<>();
		m.put("name", getScriptName());
		return m;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		return null;
	}

	@Override
	public String getTagName() {
		return "method";
	}
	
	public String getScriptName() {
		return scriptName;
	}
	
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}
	
	

}
