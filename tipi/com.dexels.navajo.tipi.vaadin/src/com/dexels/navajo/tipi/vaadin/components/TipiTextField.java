package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

public class TipiTextField extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 1561760767992331660L;
	private TextField textField;

	@Override
	public Object createContainer() {
		textField = new TextField();
		return textField;
	}

	public void setText(String s) {
		textField.setCaption(s);
	}

	public String getText() {
		return textField.getCaption();
	}
	
	  public void setComponentValue(final String name, final Object object) {
		    super.setComponentValue(name, object);
			Component v = getVaadinContainer();
		        if (name.equals("text")) {
		          v.setCaption( ""+ object);
		        }
	  }

}
