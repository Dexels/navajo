package com.dexels.navajo.jsp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public class ValueTag extends BaseNavajoTag {

	private String myProperty;

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public void setProperty(String property) {
		myProperty = property;
	}

	public int doStartTag() throws JspException {
		try {
			Navajo n = getNavajoContext().getNavajo();
			Message m = getNavajoContext().getMessage();
			Property p;
//getPageContext().getRequest().getParameterMap().
			if (m == null) {
				p = n.getProperty(myProperty);
				getPageContext().getOut().write((String)p.getTypedValue());
			} else {
				p = m.getProperty(myProperty);
				getPageContext().getOut().write((String)p.getTypedValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

}
