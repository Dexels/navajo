package com.dexels.navajo.tipi.vaadin.components;

import java.net.URL;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class TipiMenuItem extends TipiVaadinComponentImpl {
	
	private MenuItem menuItem;
	
    public TipiMenuItem() {
    }

	public void setContainer(Object c) {
		super.replaceContainer(c);
	}
    
    @SuppressWarnings("serial")
	public Object createContainer() {
    	TipiComponent parent = getTipiParent();
		Object parentMenu = parent.getContainer();
		if(parentMenu instanceof MenuItem) {
			System.err.println("PARENT ITEM FOUND ADDING MENU ITEM");
			MenuItem mbb = (MenuItem)parentMenu;
			menuItem = mbb.addItem("", new Command() {
				@Override
				public void menuSelected(MenuItem selectedItem) {
			        try {
			        	System.err.println("Menu selected!");
						performTipiEvent("onActionPerformed", null, true);
					} catch (TipiBreakException e) {
						e.printStackTrace();
					} catch (TipiException e) {
						e.printStackTrace();
					}
				}
			});
			
		} else {
			System.err.println("BAD parent! "+parentMenu);
		}

    	return menuItem;
    }
	public void disposeComponent() {
    	menuItem.getParent().removeChild(menuItem);
    	super.disposeComponent();
    }	
    protected void setComponentValue(String name, Object object) {
        
    	if ("text".equals(name)) {
            menuItem.setText("" + object);
        }
    	if ("enabled".equals(name)) {
            menuItem.setEnabled((Boolean)object);
        }

    	if ("icon".equals(name)) {
            if (object instanceof URL) {
                menuItem.setIcon(getResource(object));
            } else {
                System.err.println("Can not set button icon: I guess it failed to parse (TipiButton)");
            }
        }
//        super.setComponentValue(name, object);
    }
    public void addToContainer(Object c, Object constraints) {
    	System.err.println("Warning: adding to menuitem!");
    }
}
