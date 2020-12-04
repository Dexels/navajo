/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.MenuBar;

public class TipiMenuBar extends TipiVaadinComponentImpl {

	private static final long serialVersionUID = -8349418175376948993L;
	private MenuBar menuBar;

	public TipiMenuBar() {
    }

    @Override
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

	@Override
	protected void setComponentValue(String name, Object object) {
//    	MenuBarPane b = (MenuBarPane) getContainer();
//    	DefaultMenuModel menuModel = new DefaultMenuModel();
        super.setComponentValue(name, object);
    }

	@Override
	public void addToContainer(Object c, Object constraints) {
//		menuBar.add
		
	}  
}
