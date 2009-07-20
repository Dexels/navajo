package com.dexels.navajo.jsp.tags;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

import com.dexels.navajo.jsp.NavajoContext;

public class PostHandlerTag extends BaseNavajoTag {

	
	public int doStartTag() throws JspException {

		ServletRequest request = getPageContext().getRequest();
		Enumeration e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String value = request.getParameter(name);
			getNavajoContext().resolvePost(name,value);
		}
		return SKIP_BODY;
	}




}
