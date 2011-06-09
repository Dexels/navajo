package com.dexels.navajo.tipi.vaadin;


import com.vaadin.ui.Button;
import com.vaadin.ui.Window;

public class LoginDialog extends Window {

	private static final long serialVersionUID = 1L;

	public LoginDialog() {
		setWidth("300px");
		GenericPropertyComponent gc = new GenericPropertyComponent();
		addComponent(gc);
		GenericPropertyComponent gc2 = new GenericPropertyComponent();
		addComponent(gc2);
		Button button = new Button();
		button.setCaption("Mujaheddinaaaaa");
		addComponent(button);
	}
}
