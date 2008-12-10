package com.dexels.navajo.jsp.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;

public class TableTag extends BaseNavajoTag {

	private String myMessage;
	private String myService;

	@Override
	public int doEndTag() throws JspException {
		return 0;
	}

	public void setMessage(String message) {
		myMessage = message;
	}

	public int doStartTag() throws JspException {
		try {
			Navajo n;
			if (myService == null) {
				n = getNavajoContext().getNavajo();
			} else {
				n = getNavajoContext().getNavajo(myService);
			}
		
			Message m = n.getMessage(myMessage);
			getNavajoContext().getOutputWriter().writeTable(m, getPageContext().getOut());
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
