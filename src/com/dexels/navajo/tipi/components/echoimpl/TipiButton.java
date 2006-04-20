package com.dexels.navajo.tipi.components.echoimpl;

import java.net.URL;

import nextapp.echo2.app.Alignment;
import echopointng.ContainerEx;
import echopointng.PushButton;
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

public class TipiButton extends TipiEchoComponentImpl {
    private PushButton myButton;

    public TipiButton() {
    }

    public Object createContainer() {
        // ContainerEx ex = new ContainerEx();
        myButton = new PushButton();
        // ex.add(myButton);
        // b.setIconTextMargin(new Extent(10));
        myButton.setTextAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));

        return myButton;
        // return b;
    }

    /**
     * getComponentValue
     * 
     * @param name
     *            String
     * @return Object
     * @todo Implement this
     *       com.dexels.navajo.tipi.components.core.TipiComponentImpl method
     */
    protected Object getComponentValue(String name) {
        return "";
    }

    public Object getActualComponent() {
        return myButton;
    }

    /**
     * setComponentValue
     * 
     * @param name
     *            String
     * @param object
     *            Object
     * @todo Implement this
     *       com.dexels.navajo.tipi.components.core.TipiComponentImpl method
     */
    protected void setComponentValue(String name, Object object) {
        // Button b = (Button) getContainer();
        if ("text".equals(name)) {
            myButton.setText("" + object);
        }
        if ("icon".equals(name)) {
            if (object instanceof URL) {
                URL u = (URL) object;
                myButton.setIcon(new URLImageReference(u));
            } else {
                System.err.println("Can not set button icon: I guess it failed to parse (TipiButton)");
            }
        }
        super.setComponentValue(name, object);
    }

}
