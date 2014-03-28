package com.dexels.navajo.jsp.tags;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstanceTag extends BaseNavajoTag {

	
	private final static Logger logger = LoggerFactory
			.getLogger(InstanceTag.class);
	
	@Override
	public int doStartTag() throws JspException {

		ServletRequest request = getPageContext().getRequest();
		String instance = request.getParameter("instance");
		String sessionId =  getPageContext().getSession().getId();
		logger.debug("Tag Session Id: "+sessionId+" setting to instance: "+instance);
		if(instance!=null) {
			getPageContext().getSession().setAttribute("selectedInstance", instance);
		}
		return SKIP_BODY;
	}




}
