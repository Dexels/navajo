/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.vaadin.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiMenuSeparator.class);
	
	
    public TipiMenuSeparator() {
    }

	@Override
	public void setContainer(Object c) {
		super.replaceContainer(c);
	}
    
    
	@Override
	public Object createContainer() {
    	TipiComponent parent = getTipiParent();
		Object parentMenu = parent.getContainer();
		if(parentMenu instanceof MenuItem) {
			MenuItem mbb = (MenuItem)parentMenu;
			menuItem = mbb.addSeparator();
						
		} else {
			logger.warn("BAD parent, it should be a MenuItem "+parentMenu);
		}

    	return menuItem;
    }
	@Override
	public void disposeComponent() {
    	menuItem.getParent().removeChild(menuItem);
    	super.disposeComponent();
    }	

}
