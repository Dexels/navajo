package com.dexels.navajo.jsp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;

public class DebugTag extends BaseNavajoTag {

	private String myProperty;

	@Override
	public int doEndTag() throws JspException {
		return 0;
	}

	public void setProperty(String property) {
		myProperty = property;
	}

	public int doStartTag() throws JspException {
		getNavajoContext().debug();
		return EVAL_BODY_INCLUDE;
	}

}
