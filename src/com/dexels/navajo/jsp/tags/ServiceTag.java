package com.dexels.navajo.jsp.tags;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Navajo;

public class ServiceTag extends BaseNavajoTag {

	private String myService;

	@Override
	public int doEndTag() throws JspException {
		getNavajoContext().popNavajo();
		return 0;
	}

	public void setService(String service) {
		myService = service;
	}

	@Override
	public int doStartTag() throws JspException {
		Navajo m;
		try {
			m = getNavajoContext().getNavajo(myService);
			getNavajoContext().pushNavajo(m);
		} catch (ClientException e) {
			e.printStackTrace();
		}

		return EVAL_BODY_INCLUDE;
	}

}
