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
		System.err.println(">!@#>!@#$>!@#Œ calling: "+myService);
		
		assertTest();
		if(myService==null || "".equals(myService)) {
			throw new JspException("Error calling service: No service supplied!");
		}
		try {
			if (myNavajo==null) {
				getNavajoContext().callService(myService);
			} else {
				Navajo navajo = getNavajoContext().getNavajo(myNavajo);
				System.err.println("Calling with navajo:");
				if(navajo!=null) {
					try {
						navajo.write(System.err);
					} catch (NavajoException e) {
						e.printStackTrace();
					}
				}
				getNavajoContext().callService(myService, navajo);
			}
		} catch (ClientException e) {
			e.printStackTrace();
			throw new JspException("Navajo service error while calling service: "+myService,e);
		}
		Navajo nn = null;
			nn = getNavajoContext().getNavajo(myService);
		
		if(nn==null) {
			throw new JspException("Unknown Navajo service error while calling service: "+myService);
		}
		System.err.println("Service called: "+myService);
		Message error = nn.getMessage("error");
		if(error!=null) {
			StringWriter sw = new StringWriter();
			try {
				error.write(sw);
			} catch (NavajoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			getNavajoContext().pushMessage(error);
//			try {
//				getPageContext().include("tml/writemessage.jsp");
//			} catch (ServletException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			getNavajoContext()
//			throw new JspException("Server-side service error while calling service: "+myService+"\nProblem: "+sw);
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
//			throw new JspException("Server-side service condition while calling service: "+myService+"\nProblem: "+sw);
		}
		return EVAL_BODY_INCLUDE ;
	}
	}
