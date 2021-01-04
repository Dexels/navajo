package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseNode;

public class ValueTag extends BaseNode implements NS3Compatible {

	private String condition;
	private String value;
	
	public ValueTag(Navajo n) {
		super(n);
	}
	
	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		
	}

	@Override
	public Map<String, String> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return null;
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
