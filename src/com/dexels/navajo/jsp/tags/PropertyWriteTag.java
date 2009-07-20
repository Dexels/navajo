package com.dexels.navajo.jsp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public class PropertyWriteTag extends BaseNavajoTag {

	private String myProperty;

	public int doEndTag() throws JspException {
//		getNavajoContext().setProperty(null);
		return 0;
	}

	public void setProperty(String property) {
		myProperty = property;
	}
	
	public int doStartTag() throws JspException {
		try {
			Navajo n = getNavajoContext().getNavajo();
			Message m = getNavajoContext().getMessage();
			if (m == null) {
				getNavajoContext().writeProperty(n.getProperty(myProperty), getPageContext());
			} else {
				getNavajoContext().writeProperty(m.getProperty(myProperty), getPageContext());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	} 
	

}
