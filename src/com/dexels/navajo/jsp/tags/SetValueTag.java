package com.dexels.navajo.jsp.tags;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
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
		return 0;
	}

	public void setProperty(String property) {
		myProperty = property;
	}

	@Override
	public int doStartTag() throws JspException {
		Navajo n;
		try {
			if (myService==null) {
				n = getNavajoContext().getNavajo();
			} else {
				n = getNavajoContext().getNavajo(myService);
			}
			Property p = n.getProperty(myProperty);
			p.setValue(value);
//			try {
//				getPageContext().getOut().write(
//						"Setting property: " + myProperty + " to value: "
//								+ value);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} catch (ClientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return 0;
	}

	public void setService(String service) {
		myService = service;
	}

}
