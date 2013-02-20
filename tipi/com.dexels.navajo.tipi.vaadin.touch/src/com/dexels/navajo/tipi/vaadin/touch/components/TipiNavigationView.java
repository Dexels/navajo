package com.dexels.navajo.tipi.vaadin.touch.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.addon.touchkit.ui.NavigationView;

public class TipiNavigationView extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = -5043895594246857632L;
	private NavigationView navigationView;

	@Override
	public Object createContainer() {
		navigationView = new NavigationView();
//		nm.setSizeFull();
//		nm.setHeight("400px");
//		Button b = new Button("aap");
		navigationView.getNavigationBar().setVisible(true);
//		VerticalComponentGroup componentGroup = new VerticalComponentGroup();
//		 
//		// Name field
//		Component textField = new TextField("Name");
//		textField.setWidth("100%");
//		componentGroup.addComponent(textField);
//		 
//		// Email field
//		EmailField emailField = new EmailField("Email");
//		emailField.setWidth("100%");
//		componentGroup.addComponent(emailField);
//		 
//		// Number field
//		NumberField numberField = new NumberField("Age");
//		numberField.setWidth("100%");
//		componentGroup.addComponent(numberField);
//		nm.setContent(componentGroup);
		return navigationView;
	}

	public void setComponentValue(final String name, final Object object) {
	    super.setComponentValue(name, object);
	        if (name.equals("title")) {
	        	navigationView.getNavigationBar().setCaption( (String) object);
	        }
	}
}
