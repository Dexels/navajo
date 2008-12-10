package com.dexels.navajo.jsp.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;

public class WriteServiceTag extends BaseNavajoTag  {

	private String myService;

	public void setService(String service) {
		myService = service;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			Navajo n;
			if (myService == null) {
				n = getNavajoContext().getNavajo();
			} else {
				n = getNavajoContext().getNavajo(myService);
			}
			getNavajoContext().writeService(n,getPageContext());
		} catch (ClientException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return EVAL_BODY_INCLUDE;
	}

}
