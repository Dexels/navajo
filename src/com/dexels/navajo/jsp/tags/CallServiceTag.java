package com.dexels.navajo.jsp.tags;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;

public class CallServiceTag extends BaseNavajoTag {

		private String myService;
	private String myNavajo;

	@Override
	public int doEndTag() throws JspException {
		getNavajoContext().popNavajo();
		return EVAL_BODY_INCLUDE;
	}
	
	public void setService(String service) {
		myService = service;
	}

	public void setNavajo(String navajo) {
		myNavajo = navajo;
	}

	
	@Override
	public int doStartTag() throws JspException {
		assertTest();
		try {
			if (myNavajo==null) {
				getNavajoContext().callService(myService);
			} else {
				getNavajoContext().callService(myService, getNavajoContext().getNavajo(myNavajo));
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE ;
	}

	

	



}
