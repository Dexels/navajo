package com.dexels.navajo.jsp.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import com.dexels.navajo.client.context.NavajoContext;

public abstract class BaseNavajoTag implements Tag {
	private PageContext myPageContext = null;
	private Tag myParent = null;

	@Override
	public int doEndTag() throws JspException {
		return 0;
	}


	protected NavajoContext getNavajoContext() {
		return (NavajoContext) getPageContext().findAttribute("navajoContext");
	}
	
	@Override
	public Tag getParent() {
		return myParent;
	}

	@Override
	public void release() {

	}

	public PageContext getPageContext() {
		return myPageContext;
	}
	
	@Override
	public void setPageContext(PageContext p) {
		myPageContext = p;
		
	}

	@Override
	public void setParent(Tag p) {
		myParent = p;
	}
	protected void assertTest() {
		if(getNavajoContext()==null) {
			throw new IllegalStateException("Set the client first using a nav:client tag");
		}
	}

}
