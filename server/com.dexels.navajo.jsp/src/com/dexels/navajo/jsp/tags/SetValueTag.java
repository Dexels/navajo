package com.dexels.navajo.jsp.tags;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public class SetValueTag extends BaseNavajoTag {

	private String myProperty;
	private String value;
	private String myService;

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	public void setProperty(String property) {
		myProperty = property;
	}

	@Override
	public int doStartTag() throws JspException {
		Navajo n;
		if (myService==null) {
			n = getNavajoContext().getNavajo();
		} else {
			n = getNavajoContext().getNavajo(myService);
		}
		Property p = n.getProperty(myProperty);
		if(p==null) {
			throw new IllegalArgumentException("Can't locate property: "+myProperty+" in service: "+myService);
		}
		p.setValue(value);
		return 0;
	}

	public void setService(String service) {
		myService = service;
	}

}
