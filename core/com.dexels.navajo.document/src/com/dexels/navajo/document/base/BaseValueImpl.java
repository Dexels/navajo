package com.dexels.navajo.document.base;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Navajo;

public class BaseValueImpl extends BaseNode {

	String value;
	String condition;

	// text = "<![CDATA[ " + t + " ]]>"; 
	public BaseValueImpl(Navajo n, String value) {
		super(n);
		this.value = value;
	}

	public BaseValueImpl(Navajo n) {
		super(n);
	}

	@Override
	public Map<String, String> getAttributes() {
		Map<String,String> m = new HashMap<>();
		if ( condition != null && !"".equals(condition)) {
			m.put("condition", condition);
		}
		return m;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		return null;
	}

	@Override
	public String getTagName() {
		return "value";
	}

	@Override
	public boolean hasTextNode() {
		return true;
	}

	public void writeText(Writer w) throws IOException  {
		String  text = "<![CDATA[\n" + value + "\n]]>";  
		w.write(text);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
}
