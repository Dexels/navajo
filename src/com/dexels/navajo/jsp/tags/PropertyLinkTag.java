package com.dexels.navajo.jsp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public class PropertyLinkTag extends BaseNavajoTag {

	private String myProperty;
	private String myLabel;
	private String myPage;
	private String myLabelProperty;

	
	public void setPage(String page) {
		this.myPage = page;
	}

	public void setLabel(String page) {
		this.myPage = page;
	}

	public void setLabelProperty(String labelProperty) {
		this.myLabelProperty = labelProperty;
	}

	
	@Override
	public int doEndTag() throws JspException {
		return 0;
	}
	

	public void setProperty(String property) {
		myProperty = property;
	}

	public int doStartTag() throws JspException {
		try {
			getPageContext().getOut().write("<a class='link' href='"+myPage);
			Navajo n = getNavajoContext().getNavajo();
			Property p = n.getProperty(myProperty);
			
			getNavajoContext().writeProperty(p,getPageContext());
		} catch (IOException e) {
			e.printStackTrace();
//		} catch (ClientException e) {
//			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

//	public void setService(String service) {
//		myService = service;
//	}

	



}
