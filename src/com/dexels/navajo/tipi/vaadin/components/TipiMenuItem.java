package com.dexels.navajo.tipi.vaadin.components;

import java.net.URL;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.vaadin.components.base.TipiVaadinComponentImpl;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * 
 * @author frank
 *
 */
public class TipiMenuItem extends TipiVaadinComponentImpl {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1259593529870514964L;
	private MenuItem menuItem;
	
    public TipiMenuItem() {
    }

	public void setContainer(Object c) {
		super.replaceContainer(c);
	}
    
    
	public Object createContainer() {
    	TipiComponent parent = getTipiParent();
		Object parentMenu = parent.getContainer();
		if(parentMenu instanceof MenuItem) {
			MenuItem mbb = (MenuItem)parentMenu;
			menuItem = mbb.addItem("", new Command() {
				private static final long serialVersionUID = 8498865671721012500L;

				@Override
				public void menuSelected(MenuItem selectedItem) {
			        try {
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
    	//Ignoring adding to menuitem
    }
}
