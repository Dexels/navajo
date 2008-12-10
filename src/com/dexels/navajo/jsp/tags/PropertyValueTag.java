package com.dexels.navajo.jsp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;

public class PropertyValueTag extends BaseNavajoTag {

	private String myProperty;

	@Override
	public int doEndTag() throws JspException {
		return 0;
	}

	public void setProperty(String property) {
		myProperty = property;
	}

	public int doStartTag() throws JspException {
		try {
			Navajo n = getNavajoContext().getNavajo();
			Message m = getNavajoContext().getMessage();
			Property p;
			if (m == null) {
				p = n.getProperty(myProperty);
				getNavajoContext().writeProperty(p, getPageContext());
			} else {
				p = m.getProperty(myProperty);
				try {
					m.write(System.err);
				} catch (NavajoException e) {
					e.printStackTrace();
				}
				getNavajoContext().writeProperty(p, getPageContext());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}

}
