package com.dexels.navajo.jsp.tags;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.jsp.NavajoContext;

public class ClientTag extends BaseNavajoTag {

	private String server;
	private String username;
	private String password;
	
	public void setServer(String server) {
		this.server = server;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	@Override
	public int doStartTag() throws JspException {
//		getPageContext().setAttribute("NavajoContext", new NavajoContext(getPageContext(), server,username,password));
		NavajoContext nc = (NavajoContext) getPageContext().findAttribute("NavajoContext");
//		getPageContext().findAttribute("NavajoContext");
		nc.setupClient(server,username,password,getPageContext());
		//		getPageContext().setAttribute("Aap", "Noot");
		return EVAL_BODY_INCLUDE;
	}




}
