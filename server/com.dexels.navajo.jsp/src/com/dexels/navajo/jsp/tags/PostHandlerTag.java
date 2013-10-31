package com.dexels.navajo.jsp.tags;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PostHandlerTag extends BaseNavajoTag {

	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(PostHandlerTag.class);
	
	@Override
	public int doStartTag() throws JspException {

		ServletRequest request = getPageContext().getRequest();
		@SuppressWarnings("rawtypes")
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String value = request.getParameter(name);
			getNavajoContext().resolvePost(name,value);
		}
		return SKIP_BODY;
	}




}
