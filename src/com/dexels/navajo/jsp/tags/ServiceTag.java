package com.dexels.navajo.jsp.tags;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Navajo;

public class ServiceTag extends BaseNavajoTag {

	private String myService;
	public int doEndTag() throws JspException {
		getNavajoContext().popNavajo();
		return EVAL_PAGE;
	}

	public void setService(String service) {
		myService = service;
	}

	public int doStartTag() throws JspException {
		Navajo m;
			m = getNavajoContext().getNavajo(myService);
			if(m==null) {
				throw new IllegalStateException("Can not push navajo on navajo stack. Service: "+myService+" is not found");
			}
			getNavajoContext().pushNavajo(m);

		return EVAL_BODY_INCLUDE;
	}

}
