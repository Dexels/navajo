package com.dexels.navajo.jsp.tags;

import javax.servlet.jsp.JspException;

public class ContextTag extends BaseNavajoTag {

	public int doStartTag() throws JspException {
		
		return EVAL_BODY_INCLUDE;
	}

}
