package com.dexels.navajo.jsp.tags;

import java.io.StringReader;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.types.Binary;

public class ServiceTag extends BaseNavajoTag {

	private String myService;
	private Binary serviceBinary;
	private String serviceString;
	

	public void setServiceBinary(Binary serviceBinary) {
		this.serviceBinary = serviceBinary;
	}

	public void setServiceString(String serviceString) {
		this.serviceString = serviceString;
	}

	public int doEndTag() throws JspException {
		getNavajoContext().popNavajo();
		return EVAL_PAGE;
	}

	public void setService(String service) {
		myService = service;
	}

	public int doStartTag() throws JspException {
		Navajo m;
			if (myService!=null) {
				m = getNavajoContext().getNavajo(myService);
				if (m == null) {
					throw new IllegalStateException("Can not push navajo on navajo stack. Service: " + myService + " is not found");
				}
				getNavajoContext().pushNavajo(m);
			} else if(serviceBinary!=null){
				Binary b = serviceBinary;
				if(b==null) {
					throw new IllegalStateException("Either supply a service name or  " + myService + " is not found");
				}
				Navajo n = NavajoFactory.getInstance().createNavajo(b.getDataAsStream());
				getNavajoContext().pushNavajo(n);
			} else {
				StringReader sw = new StringReader(serviceString);
				Navajo n = NavajoFactory.getInstance().createNavajo(sw);
				getNavajoContext().pushNavajo(n);
				
			}
		return EVAL_BODY_INCLUDE;
	}

}
