package com.dexels.navajo.tipi.vaadin.components;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiMenuItem.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -1259593529870514964L;
	private MenuItem menuItem;
	
    public TipiMenuItem() {
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
			menuItem = mbb.addItem("", new Command() {
				private static final long serialVersionUID = 8498865671721012500L;

				@Override
				public void menuSelected(MenuItem selectedItem) {
			        try {
						performTipiEvent("onActionPerformed", null, true);
					} catch (TipiBreakException e) {
						logger.error("Error: ",e);
					} catch (TipiException e) {
						logger.error("Error: ",e);
					}
				}
			});
			
		} else {
			logger.warn("BAD parent, it is not a MenuItem: {}",parentMenu);
		}

    	return menuItem;
    }
	@Override
	public void disposeComponent() {
    	menuItem.getParent().removeChild(menuItem);
    	super.disposeComponent();
    }	
    @Override
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
                logger.debug("Can not set button icon: I guess it failed to parse (TipiButton)");
            }
        }
    	if ("visible".equals(name)) {
    		logger.debug("SETTING VISIBLE: "+object+" for component: "+menuItem.getText());
            menuItem.setVisible((Boolean) object);
        }
    	if ("style".equals(name)) {
            menuItem.setStyleName((String)object);
        }
//        super.setComponentValue(name, object);
    }
    @Override
	public void addToContainer(Object c, Object constraints) {
    	//Ignoring adding to menuitem
    }
}
