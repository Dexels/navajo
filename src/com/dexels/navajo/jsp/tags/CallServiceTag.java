package com.dexels.navajo.jsp.tags;

import java.io.StringWriter;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;

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
			throw new JspException("Navajo service error while calling service: "+myService,e);
		}
		Navajo nn = null;
		try {
			nn = getNavajoContext().getNavajo(myService);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(nn==null) {
			throw new JspException("Unknown Navajo service error while calling service: "+myService);
		}
		Message error = nn.getMessage("error");
		if(error!=null) {
			StringWriter sw = new StringWriter();
			try {
				error.write(sw);
			} catch (NavajoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new JspException("Server-side service error while calling service: "+myService+"\nProblem: "+sw);
		}
		error = nn.getMessage("ConditionErrors");
		if(error!=null) {
			StringWriter sw = new StringWriter();
			try {
				error.write(sw);
			} catch (NavajoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new JspException("Server-side service condition while calling service: "+myService+"\nProblem: "+sw);
		}
		return EVAL_BODY_INCLUDE ;
	}

	

	



}
