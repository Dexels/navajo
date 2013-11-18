package com.dexels.navajo.tipi.vaadin.touch.components;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.addon.touchkit.ui.Popover;

public class TipiPopover extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = -5043895594246857632L;
	private Popover popover;

	@Override
	public Object createContainer() {
		popover = new Popover();
		popover.setHeight("300px");
		popover.center();
		popover.setClosable(true);
//		nm.setSizeFull();
//		nm.setHeight("400px");
//		Button b = new Button("aap");
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
		return popover;
	}

	@Override
	public void setComponentValue(final String name, final Object object) {
	    super.setComponentValue(name, object);
	        if (name.equals("title")) {
	        	popover.setCaption( (String) object);
	        }
	        if ("icon".equals(name)) {
	        	popover.setIcon( getResource(object));
	        }
	        if ("h".equals(name)) {
	        	popover.setHeight(""+object+"px");
	        }
	        if ("w".equals(name)) {
	        	popover.setWidth(""+object+"px");
	        }
	        if ("x".equals(name)) {
	        	popover.setPositionX((Integer) object);
	        }
	        if ("y".equals(name)) {
	        	popover.setPositionY((Integer) object);
	        }
	        if ("closable".equals(name)) {
	        	popover.setClosable((Boolean) object);
	        }
	        if ("resizable".equals(name)) {
	        	popover.setResizable((Boolean) object);
	        }
	}
    @Override
	protected synchronized void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
        super.performComponentMethod(name, compMeth, event);
        if (name.equals("show")) {
			getVaadinApplication().getMainWindow().addWindow(popover);
        }
        if (name.equals("hide") || name.equals("dispose")) {
			getVaadinApplication().getMainWindow().removeWindow(popover);
        }
    }
}
