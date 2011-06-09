package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

public class TipiLabel extends TipiVaadinComponentImpl {

	private Label label;

	@Override
	public Object createContainer() {
		label = new Label();
		return label;
	}

	public void setText(String s) {
		label.setCaption(s);
	}

	public String getText() {
		return label.getCaption();
	}
	
	  public void setComponentValue(final String name, final Object object) {
		    super.setComponentValue(name, object);
			Component v = getVaadinContainer();
		        if (name.equals("text")) {
		          v.setCaption( (String) object);
		        }
		        if ("icon".equals(name)) {
	                v.setIcon( getResource(object));
		        }
	  }

}
