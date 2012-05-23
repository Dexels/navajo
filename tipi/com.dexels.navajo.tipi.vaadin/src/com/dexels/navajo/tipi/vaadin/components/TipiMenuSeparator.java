package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * 
 * @author frank
 *
 */
public class TipiMenuSeparator extends TipiVaadinComponentImpl {
	
	private static final long serialVersionUID = 4197538016180545553L;
	private MenuItem menuItem;
	
    public TipiMenuSeparator() {
    }

	public void setContainer(Object c) {
		super.replaceContainer(c);
	}
    
    
	public Object createContainer() {
    	TipiComponent parent = getTipiParent();
		Object parentMenu = parent.getContainer();
		if(parentMenu instanceof MenuItem) {
			MenuItem mbb = (MenuItem)parentMenu;
			menuItem = mbb.addSeparator();
						
		} else {
			System.err.println("BAD parent! "+parentMenu);
		}

    	return menuItem;
    }
	public void disposeComponent() {
    	menuItem.getParent().removeChild(menuItem);
    	super.disposeComponent();
    }	

}
