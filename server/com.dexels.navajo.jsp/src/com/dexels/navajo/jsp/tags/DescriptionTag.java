package com.dexels.navajo.jsp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public class DescriptionTag extends BaseNavajoTag  {

	private String myProperty;
	private String myService;
	
	private final static Logger logger = LoggerFactory
			.getLogger(DescriptionTag.class);
	
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	
	public void setProperty(String property) {
		myProperty = property;
	}

	public int doStartTag() throws JspException {
		try {
			Navajo n;
			if(myService==null) {
				n = getNavajoContext().getNavajo();
			} else {
				n = getNavajoContext().getNavajo(myService);
			}
			Property p = n.getProperty(myProperty);
			getPageContext().getOut().write(p.getDescription());
		} catch (IOException e) {
			logger.error("Error: ", e);
		}
		return EVAL_BODY_INCLUDE;
	}

	public void setService(String service) {
		myService = service;
	}

	



}
