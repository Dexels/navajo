package com.dexels.navajo.jsp.tags;

import java.io.StringReader;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;

public class TmlTag extends BaseNavajoTag {

	private Object content;
	@Override
	public int doEndTag() throws JspException {
		getNavajoContext().popNavajo();
		return EVAL_PAGE;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int doStartTag() throws JspException {
		Navajo m= null;
 			String ss = content.toString();
			System.err.println("String::::: "+ss+"<<<<<<<<<");
			m = NavajoFactory.getInstance().createNavajo(new StringReader(ss));
			if(m==null) {
				throw new IllegalStateException("Can not push navajo on navajo stack. Service doesnt seem to parse!");
			}
			getNavajoContext().pushNavajo(m);

		return EVAL_BODY_INCLUDE;
	}

}
