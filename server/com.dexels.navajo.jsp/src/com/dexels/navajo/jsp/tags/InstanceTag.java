package com.dexels.navajo.jsp.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstanceTag extends BaseNavajoTag {

	
	private final static Logger logger = LoggerFactory
			.getLogger(InstanceTag.class);
	
	@Override
	public int doStartTag() throws JspException {

		HttpServletRequest request = (HttpServletRequest) getPageContext().getRequest();
		String instance = request.getParameter("instance");
		if(instance==null) {
			instance = request.getHeader("X-Navajo-Instance");
		}
		
		String sessionId =  getPageContext().getSession().getId();
		logger.debug("Tag Session Id: "+sessionId+" setting to instance: "+instance);
		if(instance!=null) {
			getPageContext().getSession().setAttribute("selectedInstance", instance);
		}
		return SKIP_BODY;
	}




}
