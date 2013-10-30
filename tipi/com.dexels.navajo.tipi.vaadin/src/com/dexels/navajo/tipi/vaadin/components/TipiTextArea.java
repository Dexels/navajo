package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;

public class TipiTextArea extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 1561760767992331660L;
	private TextArea textField;

	@Override
	public Object createContainer() {
		textField = new TextArea();
		return textField;
	}

	public void setText(String s) {
		textField.setCaption(s);
	}

	public String getText() {
		return textField.getCaption();
	}
	
	  @Override
	public void setComponentValue(final String name, final Object object) {
		    super.setComponentValue(name, object);
			Component v = getVaadinContainer();
		        if (name.equals("text")) {
		          v.setCaption( ""+ object);
		        }
	  }

}
