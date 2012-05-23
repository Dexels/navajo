package com.dexels.navajo.jsp.tags;

import javax.servlet.jsp.JspException;

public class ResetTag extends BaseNavajoTag  {

	public int doStartTag() throws JspException {
		getNavajoContext().reset();
		return EVAL_BODY_INCLUDE;
	}

}
