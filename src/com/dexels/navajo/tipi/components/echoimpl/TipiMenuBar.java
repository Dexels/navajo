package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.extras.app.MenuBarPane;
import echopointng.MenuBar;

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

public class TipiMenuBar extends TipiEchoDataComponentImpl {
    public TipiMenuBar() {
    }

    public Object createContainer() {
    	
        MenuBar b = new MenuBar();
//        b.setStyleName("Default");
        b.setTopOffset(0);

        return b;
    }

    protected void setComponentValue(String name, Object object) {
        MenuBar b = (MenuBar) getContainer();
        if ("text".equals(name)) {
            b.setText("" + object);
        }
        super.setComponentValue(name, object);
    }

}
