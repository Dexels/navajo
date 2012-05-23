package com.dexels.navajo.tipi.components.echoimpl;

import java.net.URL;

import com.dexels.navajo.tipi.TipiException;

import echopointng.image.URLImageReference;

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

public class TipiMenuItem extends TipiEchoComponentImpl {
    /**
	 * 
	 */
	private static final long serialVersionUID = 9163793783532263272L;
	public TipiMenuItem() {
    }

    public Object createContainer() {
//        MenuItem b = new MenuItem();
//        b.setStyleName("Default");
//      openRecentMenuModel.addItem(new DefaultOptionModel("open-recent-1", "Hotel.pdf", null));
//      openRecentMenuModel.addItem(new DefaultOptionModel("open-recent-2", "Alpha.txt", null));
        EchoMutableObjectModel b = new EchoMutableObjectModel(this);
//        b.setId();
        
        return b;
    }
    
    public void performAction() throws TipiException {
        performTipiEvent("onActionPerformed", null, false);
     }

    protected void setComponentValue(String name, Object object) {
    	EchoMutableObjectModel im = (EchoMutableObjectModel) getContainer();
        
    	if ("text".equals(name)) {
            im.setText("" + object);
        }
        if ("icon".equals(name)) {
            if (object instanceof URL) {
                URL u = (URL) object;
                im.setIcon(new URLImageReference(u));
            } else {
                System.err.println("Can not set button icon: I guess it failed to parse (TipiButton)");
            }
        }
        super.setComponentValue(name, object);
    }
    public void addToContainer(Object c, Object constraints) {
    	System.err.println("Warning: adding to menuitem!");
    }
}
