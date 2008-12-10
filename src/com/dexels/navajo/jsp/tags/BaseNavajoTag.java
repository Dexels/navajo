package com.dexels.navajo.jsp.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import com.dexels.navajo.jsp.NavajoContext;

public abstract class BaseNavajoTag implements Tag {
	private PageContext myPageContext = null;
	private Tag myParent = null;

	public int doEndTag() throws JspException {
		return 0;
	}


	protected NavajoContext getNavajoContext() {
		return (NavajoContext) getPageContext().findAttribute("NavajoContext");
	}
	
	public Tag getParent() {
		return myParent;
	}

	public void release() {

	}

	public PageContext getPageContext() {
		return myPageContext;
	}
	
	public void setPageContext(PageContext p) {
		myPageContext = p;
		
	}

	public void setParent(Tag p) {
		myParent = p;
	}
	protected void assertTest() {
		if(getNavajoContext()==null) {
			throw new IllegalStateException("Set the client first using a nav:client tag");
		}
	}

}
