package com.dexels.navajo.jsp.tags;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

public class InstanceTag extends BaseNavajoTag {

	
	
	@Override
	public int doStartTag() throws JspException {

		ServletRequest request = getPageContext().getRequest();
		String instance = request.getParameter("instance");
		String sessionId =  getPageContext().getSession().getId();
		System.err.println("Tag Session Id: "+sessionId);
		if(instance!=null) {
			getPageContext().getSession().setAttribute("selectedInstance", instance);
		}
		return SKIP_BODY;
	}




}
