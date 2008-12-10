package com.dexels.navajo.jsp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public class DescriptionTag extends BaseNavajoTag  {

	private String myProperty;
	private String myService;

	@Override
	public int doEndTag() throws JspException {
		return 0;
	}
	
	public void setProperty(String property) {
		myProperty = property;
	}

	@Override
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
			e.printStackTrace();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

	public void setService(String service) {
		myService = service;
	}

	



}
