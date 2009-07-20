package com.dexels.navajo.jsp.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;

public class PropertyTag extends BaseNavajoTag {

	private String propertyName;
	private Property property;

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property message) {
		this.property = message;
	}

	@Override
	public int doEndTag() throws JspException {
		getNavajoContext().popMessage();
		return 0;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public int doStartTag() throws JspException {
			Navajo n;
			Message parent = getNavajoContext().getMessage();
			if(property==null) {
				if(parent!=null) {
					property = parent.getProperty(propertyName);
				} else {
					n = getNavajoContext().getNavajo();
					property = n.getProperty(propertyName);
				}
			}
			getNavajoContext().pushProperty(property);
			
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public void release() {
		property = null; 
		propertyName = null;
		super.release();
	}
	
}
