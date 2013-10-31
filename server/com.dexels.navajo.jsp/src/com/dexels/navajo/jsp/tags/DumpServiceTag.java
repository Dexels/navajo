package com.dexels.navajo.jsp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;

public class DumpServiceTag extends BaseNavajoTag  {

	private String myService;
	private boolean log;
	
	private final static Logger logger = LoggerFactory
			.getLogger(DumpServiceTag.class);
	
	public boolean isLog() {
		return log;
	}

	public void setLog(boolean log) {
		this.log = log;
	}

	public void setService(String service) {
		myService = service;
	}

	@Override
	public int doStartTag() throws JspException {
		Navajo n = null;
		if(myService!=null) {
				n = getNavajoContext().getNavajo(myService);
		} else {
			n = getNavajoContext().getNavajo();
			myService = n.getHeader().getRPCName();
		}

		getNavajoContext().getNavajo();
		try {
			if (log) {
				if (myService == null) {
					getNavajoContext().getNavajo().write(System.err);
				} else {
					getNavajoContext().getNavajo(myService).write(System.err);
				}
			} else {
				if (myService == null) {
					getNavajoContext().getNavajo().write(getPageContext().getOut());
				} else {
					getNavajoContext().getNavajo(myService).write(
							getPageContext().getOut());
				}
			}
			getPageContext().getOut().write("Dumped: " + myService);
		} catch (NavajoException e) {
			logger.error("Error: ", e);
		} catch (IOException e) {
			logger.error("Error: ", e);
		}

		return EVAL_BODY_INCLUDE;
	}

}
