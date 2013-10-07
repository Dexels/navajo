package com.dexels.navajo.jsp.tags;

import javax.servlet.jsp.JspException;

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
		getNavajoContext().popProperty();
		property = null;
		propertyName = null;
		return EVAL_PAGE;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public int doStartTag() throws JspException {
		
			Navajo n;
			if(property==null) {
				Message parent = getNavajoContext().getMessage();
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
