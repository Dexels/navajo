package com.dexels.navajo.tipi.vaadin.components;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiMenu.class);
	
    public TipiMenu() {
    }

	@Override
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
    	if(menuItem!=null && menuItem.getParent()!=null) {
        	menuItem.getParent().removeChild(menuItem);
    	}
    	super.disposeComponent();
    }	
	// skip the vaadinComponent's setContainer
	@Override
	public void setContainer(Object c) {
		super.replaceContainer(c);
	}

    @Override
	protected void setComponentValue(String name, Object object) {
        
    	if ("text".equals(name)) {
            menuItem.setText("" + object);
        }
        if ("icon".equals(name)) {
            if (object instanceof URL) {
                menuItem.setIcon(getResource(object));
            } else {
                logger.warn("Can not set menu icon: I guess it failed to parse (TipiMenu)");
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
    @Override
	public void addToContainer(Object c, Object constraints) {
    }
}
