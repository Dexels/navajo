package com.dexels.navajo.jsp.tags;

import javax.servlet.jsp.JspException;

public class LogTag extends BaseNavajoTag {

	private String text;
	
	public void setText(String text) {
		this.text = text;
	}


	
	@Override
	public int doStartTag() throws JspException {
		System.err.println("Log: "+text+"\n");
		return SKIP_BODY;
	}




}
