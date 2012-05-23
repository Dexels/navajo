package com.dexels.navajo.tipi.components.echoimpl;

import java.net.URL;

import nextapp.echo2.app.Label;
import echopointng.LabelEx;
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

public class TipiLabel extends TipiEchoComponentImpl {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2855077377301927835L;

	public TipiLabel() {
    }

    public Object createContainer() {
        Label b = new LabelEx();
//        b.setStyleName("Default");
        return b;
    }
//    public void processStyles() {
////      System.err.println("Processing styles.... "+styleHintMap);
//      super.processStyles();
//      boolean bl = "true".equals(getStyle("linewrap")) || "yes".equals(getStyle("linewrap"));
//     	  ((LabelEx)getContainer()).setLineWrap(bl);
//     
//       }

    protected void setComponentValue(String name, Object object) {
        Label b = (Label) getContainer();
        if ("text".equals(name)) {
            b.setText("" + object);
        }
        if ("visible".equals(name)) {
            b.setVisible((Boolean)object);
        }
        if ("icon".equals(name)) {
            if (object instanceof URL) {
                URL u = (URL) object;
                System.err.println("Using url for button: " + u);
                b.setIcon(new URLImageReference(u));
            } else {
                System.err.println("Can not set button icon: I guess it failed to parse (TipiButton)");
            }
        }
        super.setComponentValue(name, object);
    }

}
