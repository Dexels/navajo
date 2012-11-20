package com.dexels.navajo.jsp.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.context.NavajoContext;
import com.dexels.navajo.client.context.NavajoRemoteContext;

public class ClientTag extends BaseNavajoTag {

	private String server;
	private String username;
	private String password;
	private boolean debugAll = false;
	
	private final static Logger logger = LoggerFactory
			.getLogger(ClientTag.class);
	
	public void setServer(String server) {
		this.server = server;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDebugAll(boolean debugAll) {
		this.debugAll = debugAll;
	}

	
	public int doStartTag() throws JspException {
		NavajoContext context = (NavajoContext) getPageContext().findAttribute("navajoContext");
		NavajoRemoteContext nc = null;
		//		getPageContext().findAttribute("NavajoContext");
		if(context==null) {
			throw new JspException("No navajo context found!");
		}

		if(context instanceof NavajoRemoteContext) {
			nc = (NavajoRemoteContext)context;
		} else {
			logger.warn("Trying to set up non-remote client, ignoring");
			return SKIP_BODY;
		}
		if(server==null) {
			HttpServletRequest rr =  (HttpServletRequest) getPageContext().getRequest();
			StringBuffer sb = new StringBuffer();
			sb.append(rr.getServerName()+":");
			sb.append(rr.getServerPort());
			sb.append(rr.getContextPath());
			server = sb.toString()+"/Postman";
		}
		HttpServletRequest request = (HttpServletRequest) getPageContext().getRequest();
		String requestServerName = request.getServerName();
		int requestServerPort = request.getServerPort();
		String requestContextPath = request.getContextPath();

		nc.setupClient(server,username,password,requestServerName,requestServerPort,requestContextPath,"/Postman",debugAll);
		logger.info("JSPClient setup. Debugall:  "+debugAll);
		//		getPageContext().setAttribute("Aap", "Noot");
		return EVAL_BODY_INCLUDE;
	}
}
