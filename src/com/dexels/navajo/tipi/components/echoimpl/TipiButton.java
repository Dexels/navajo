package com.dexels.navajo.tipi.components.echoimpl;

import java.net.URL;

import com.dexels.navajo.tipi.components.echoimpl.impl.*;
import com.dexels.navajo.tipi.components.echoimpl.parsers.*;

import nextapp.echo2.app.*;
import echopointng.*;
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
    private Button myButton;

    public TipiButton() {
    }

    public Object createContainer() {
        // ContainerEx ex = new ContainerEx();
        myButton = new ButtonImpl();
        myButton.setTextAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
        myButton.setAlignment(new Alignment(Alignment.CENTER, Alignment.DEFAULT));
        // ex.add(myButton);
        // b.setIconTextMargin(new Extent(10));
        return myButton;
        // return b;
    }
    public void processStyles() {
//        System.err.println("Processing styles.... "+styleHintMap);
        super.processStyles();
        Color c = ColorParser.parseColor(getStyle("foreground"));
        if (c!=null) {
            myButton.setForeground(c);
        }
        c = ColorParser.parseColor(getStyle("background"));
        if (c!=null) {
            myButton.setBackground(c);
        }
        c = ColorParser.parseColor(getStyle("pressedforeground"));
        if (c!=null) {
            myButton.setPressedForeground(c);
        }
        c = ColorParser.parseColor(getStyle("pressedbackground"));
        if (c!=null) {
            myButton.setPressedBackground(c);
        }
        c = ColorParser.parseColor(getStyle("rolloverbackground"));
        if (c!=null) {
            myButton.setRolloverBackground(c);
        }
        c = ColorParser.parseColor(getStyle("rolloverforeground"));
        if (c!=null) {
            myButton.setRolloverForeground(c);
        }
      
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
        if ("tooptip".equals(name)) {
            myButton.setToolTipText("" + object);
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
