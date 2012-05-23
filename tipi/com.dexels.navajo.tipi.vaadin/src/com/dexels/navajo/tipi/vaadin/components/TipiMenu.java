package com.dexels.navajo.tipi.vaadin.components;

import java.net.URL;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.MenuBar;
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

public class TipiMenu extends TipiVaadinComponentImpl {
	
	private static final long serialVersionUID = 1811897469708468275L;
	private MenuItem menuItem;
	
    public TipiMenu() {
    }

	public Object createContainer() {
    	TipiComponent parent = getTipiParent();

		Object parentMenu = parent.getContainer();
		if(parentMenu instanceof MenuBar) {
			MenuBar mbb = (MenuBar)parentMenu;
			return menuItem = mbb.addItem("", null);
		}
    	return null;
    }

    @Override
	public void disposeComponent() {
    	menuItem.getParent().removeChild(menuItem);
    	super.disposeComponent();
    }	
	// skip the vaadinComponent's setContainer
	public void setContainer(Object c) {
		super.replaceContainer(c);
	}

    protected void setComponentValue(String name, Object object) {
        
    	if ("text".equals(name)) {
            menuItem.setText("" + object);
        }
        if ("icon".equals(name)) {
            if (object instanceof URL) {
                menuItem.setIcon(getResource(object));
            } else {
                System.err.println("Can not set button icon: I guess it failed to parse (TipiButton)");
            }
        }
    	if ("visible".equals(name)) {
            menuItem.setVisible((Boolean) object);
        }
    	if ("enabled".equals(name)) {
            menuItem.setEnabled((Boolean) object);
        }
    	if ("style".equals(name)) {
            menuItem.setStyleName((String)object);
        }
//        super.setComponentValue(name, object);
    }
    public void addToContainer(Object c, Object constraints) {
    }
}
