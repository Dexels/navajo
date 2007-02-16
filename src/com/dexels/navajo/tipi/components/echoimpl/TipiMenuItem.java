package com.dexels.navajo.tipi.components.echoimpl;

import java.net.URL;

import nextapp.echo2.app.ResourceImageReference;
import echopointng.MenuItem;
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
    public TipiMenuItem() {
    }

    public Object createContainer() {
        MenuItem b = new MenuItem();
//        b.setStyleName("Default");
        return b;
    }

    protected void setComponentValue(String name, Object object) {
        MenuItem b = (MenuItem) getContainer();
        if ("text".equals(name)) {
            b.setText("" + object);
        }
        if ("icon".equals(name)) {
            if (object instanceof URL) {
                URL u = (URL) object;
                b.setIcon(new URLImageReference(u));
            } else {
                System.err.println("Can not set button icon: I guess it failed to parse (TipiButton)");
            }
        }
        super.setComponentValue(name, object);
    }

}
