package com.dexels.navajo.jsp.tags;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientSetupTag extends BaseNavajoTag {

	private final static Logger logger = LoggerFactory
			.getLogger(ClientSetupTag.class);
	

	private String username;
	private String password;
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int doStartTag() throws JspException {

		String sessionId =  getPageContext().getSession().getId();
		logger.debug("Tag Session Id: "+sessionId+" setting to instance: "+username);
		if(username!=null) {
			getPageContext().getSession().setAttribute("sessionUsername", username);
		}
		if(password!=null) {
			getPageContext().getSession().setAttribute("sessionPassword", password);
		}
		return SKIP_BODY;
	}




}
