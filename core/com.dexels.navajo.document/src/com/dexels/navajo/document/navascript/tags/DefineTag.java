package com.dexels.navajo.document.navascript.tags;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.base.BaseNode;

public class DefineTag extends BaseNode implements NS3Compatible {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1969292806011683695L;
	
	String name;
	String expression;
	Navajo myNavajo;
	
	
	public DefineTag(Navajo n) {
		myNavajo = n;
	}
	
	@Override
	public Map<String, String> getAttributes() {
		Map<String,String> a = new HashMap<>();
		
		a.put("name", name);
		
		return a;
	}

	@Override
	public List<? extends BaseNode> getChildren() {
		return null;
	}

	@Override
	public String getTagName() {
		return Tags.DEFINE;
	}

	@Override
	public void formatNS3(int indent, OutputStream w) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append(NS3Utils.generateIndent(indent) );
		sb.append(NS3Keywords.DEFINE);
		sb.append(" " + NS3Constants.DOUBLE_QUOTE);
		sb.append(name);
		sb.append(NS3Constants.DOUBLE_QUOTE);
		sb.append(" : ");
		sb.append(expression);
		sb.append(NS3Constants.EOL_DELIMITER + "\n");
		w.write(sb.toString().getBytes());
		
	}

	@Override
	public boolean hasTextNode() {
		return true;
	}

	@Override
	public void writeText(Writer w) throws IOException  {
		if ( expression != null ) {
			w.write(expression + "\n");
		}
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
}
