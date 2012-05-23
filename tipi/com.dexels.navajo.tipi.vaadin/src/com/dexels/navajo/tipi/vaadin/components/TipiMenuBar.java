package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.MenuBar;

public class TipiMenuBar extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = -8349418175376948993L;
	private MenuBar menuBar;

	public TipiMenuBar() {
    }

    public Object createContainer() {
    	menuBar = new MenuBar();
    	menuBar.setImmediate(true);
    	getVaadinApplication().getMainWindow().addComponent(menuBar);
    	menuBar.setWidth("100%");
    	return menuBar;
    }
    
    

    @Override
	public void disposeComponent() {
    	getVaadinApplication().getMainWindow().removeComponent(menuBar);
		super.disposeComponent();
    }

	protected void setComponentValue(String name, Object object) {
//    	MenuBarPane b = (MenuBarPane) getContainer();
//    	DefaultMenuModel menuModel = new DefaultMenuModel();
        super.setComponentValue(name, object);
    }

	public void addToContainer(Object c, Object constraints) {
//		menuBar.add
		
	}  
}
