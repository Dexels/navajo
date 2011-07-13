package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.TextArea;

public class TipiMemo extends TipiVaadinComponentImpl {

	private TextArea textArea;
	@Override
	public Object createContainer() {
		textArea = new TextArea();
		textArea.setSizeFull();
		return textArea;
	}
	  public void setComponentValue(final String name, final Object object) {
		    super.setComponentValue(name, object);
		        if (name.equals("text")) {
		        	textArea.setValue( object);
		        }
	  }
}
