package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.dexels.navajo.tipi.vaadin.document.AdHocProperty;
import com.vaadin.data.Property;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;

public class TipiTextArea extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = 1561760767992331660L;
	private TextArea textField;
	private Property p;

	@Override
	public Object createContainer() {
		p = new AdHocProperty("", String.class) {
			@Override
			public String toString() {
				return (String) getValue();
			}
		};

		textField = new TextArea("", p);
		return textField;
	}

	@Override
	public void setComponentValue(final String name, final Object object) {
		if (name.equals("text")) {
			p.setValue(object);
			return;
		}
		super.setComponentValue(name, object);
	}

	@Override
	public Object getComponentValue(final String name) {
		if (name.equals("text")) {
			return p.getValue();
		}
		return super.getComponentValue(name);

	}

}
