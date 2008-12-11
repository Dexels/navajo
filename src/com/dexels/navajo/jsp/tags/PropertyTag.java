package com.dexels.navajo.jsp.tags;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public class PropertyTag extends BaseNavajoTag {

	private String myPath;

	public int doEndTag() throws JspException {
		getNavajoContext().setProperty(null);
		return 0;
	}

	public void setPath(String path) {
		myPath = path;
	}

	public int doStartTag() throws JspException {
		Property m;
		Message msg = getNavajoContext().getMessage();
		if(msg !=null) {
			m = getNavajoContext().getMessage().getProperty(myPath);
		} else {
			m = getNavajoContext().getNavajo().getProperty(myPath);
		}
			getNavajoContext().setProperty(m);

		return EVAL_BODY_INCLUDE;
	}

}
