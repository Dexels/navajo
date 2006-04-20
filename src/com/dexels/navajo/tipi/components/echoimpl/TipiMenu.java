package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.ResourceImageReference;
import echopointng.Menu;

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

public class TipiMenu extends TipiEchoDataComponentImpl {
    public TipiMenu() {
    }

    public Object createContainer() {
        Menu b = new Menu();
        return b;
    }

    protected void setComponentValue(String name, Object object) {
        Menu b = (Menu) getContainer();
        if ("text".equals(name)) {
            b.setText("" + object);
        }
        if ("icon".equals(name)) {
            b.setIcon(new ResourceImageReference(object.toString()));
        }
        super.setComponentValue(name, object);
    }

}
