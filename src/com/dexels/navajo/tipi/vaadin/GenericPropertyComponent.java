package com.dexels.navajo.tipi.vaadin;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

public class GenericPropertyComponent extends FormLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TextField tf = new TextField();
	public GenericPropertyComponent() {
//		FormLayout layout = new FormLayout();
//		setMargin(true);
//		setContent(layout);
		tf.setCaption("Monkeyyy");
		addComponent(tf);

	}
}
