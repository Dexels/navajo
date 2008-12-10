package com.dexels.navajo.jsp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;

public class DumpServiceTag extends BaseNavajoTag  {

	private String myService;

	public void setService(String service) {
		myService = service;
	}

	public int doStartTag() throws JspException {
		Navajo n = null;
		if(myService!=null) {
			try {
				n = getNavajoContext().getNavajo(myService);
			} catch (ClientException e) {
				throw new JspException("Error dumping service. Service: "+myService+" specified, but not found.",e);
			}
		} else {
			n = getNavajoContext().getNavajo();
			myService = n.getHeader().getRPCName();
		}

		getNavajoContext().getNavajo();
		try {
			if (myService == null) {
				getNavajoContext().getNavajo().write(getPageContext().getOut());
			} else {
				getNavajoContext().getNavajo(myService).write(
						getPageContext().getOut());
			}
			getPageContext().getOut().write("Dumped: " + myService);
		} catch (NavajoException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return EVAL_BODY_INCLUDE;
	}

}
