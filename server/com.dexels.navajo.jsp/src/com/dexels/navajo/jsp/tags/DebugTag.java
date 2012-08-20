package com.dexels.navajo.jsp.tags;

import javax.servlet.jsp.JspException;

public class DebugTag extends BaseNavajoTag {

//	private String myProperty;

	@Override
	public int doEndTag() throws JspException {
		return 0;
	}


	public int doStartTag() throws JspException {
		getNavajoContext().debug();
		return EVAL_BODY_INCLUDE;
	}

}
