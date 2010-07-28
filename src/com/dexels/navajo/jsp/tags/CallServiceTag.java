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
	private Navajo resultNavajo;
	@Override
	public int doEndTag() throws JspException {
		if(resultNavajo!=null) {
			getNavajoContext().popNavajo();			
		}
		return EVAL_PAGE;
	}
	
	public void setService(String service) {
		myService = service;
	}

	public void setNavajo(String navajo) {
		myNavajo = navajo;
	}

	
	public int doStartTag() throws JspException {
		assertTest();
		if(myService==null || "".equals(myService)) {
			throw new JspException("Error calling service: No service supplied!");
		}
		try {
			if (myNavajo==null) {
				getNavajoContext().callService(myService);
			} else {
				Navajo navajo = getNavajoContext().getNavajo(myNavajo);
				if(navajo!=null) {
					System.err.println("Using input:");
					try {
						navajo.write(System.err);
					} catch (NavajoException e) {
						e.printStackTrace();
					}
				} else {
					System.err.println("Input requested: "+myNavajo+" but not found in context!!");
				}
				getNavajoContext().callService(myService, navajo);
			}
		} catch (ClientException e) {
			e.printStackTrace();
			throw new JspException("Navajo service error while calling service: "+myService,e);
		}
		resultNavajo = getNavajoContext().getNavajo(myService);
		
		if(resultNavajo==null) {
			throw new JspException("Unknown Navajo service error while calling service: "+myService);
		}
		System.err.println("Service called: "+myService);
		Message error = resultNavajo.getMessage("error");
		if(error!=null) {
			StringWriter sw = new StringWriter();
			try {
				error.write(sw);
			} catch (NavajoException e) {
				e.printStackTrace();
			}
			// reset
			resultNavajo = null;

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
		error = resultNavajo.getMessage("ConditionErrors");
		if(error!=null) {
			StringWriter sw = new StringWriter();
			try {
				error.write(sw);
			} catch (NavajoException e) {
				e.printStackTrace();
			}
//			throw new JspException("Server-side service condition while calling service: "+myService+"\nProblem: "+sw);
			resultNavajo = null;
		}
		
		if(resultNavajo!=null) {
			getNavajoContext().pushNavajo(resultNavajo);
		}
		return EVAL_BODY_INCLUDE ;
	}
	}
