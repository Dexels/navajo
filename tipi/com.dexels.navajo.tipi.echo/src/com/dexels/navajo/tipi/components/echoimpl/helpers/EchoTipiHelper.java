/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nextapp.echo2.app.Border;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Font;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.internal.TipiEvent;

import echopointng.able.Borderable;

public class EchoTipiHelper implements TipiHelper {

	private static final long serialVersionUID = 8450401263733080156L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(EchoTipiHelper.class);
	public EchoTipiHelper() {
    }

    private TipiComponent myComponent = null;

    public void initHelper(TipiComponent tc) {
        // logger.info("initHelper: " + tc);
        myComponent = tc;
    }

    public void setComponentValue(String name, Object object) {
        // logger.info("setComponentValue: " + name);
        if (!Component.class.isInstance(myComponent.getActualComponent())) {
            if (name.equals("visible")) {
                logger.info("Sorry, should not assign a EchoHelper to a non-Echo component");
                ((Component) myComponent.getActualComponent()).setVisible(((Boolean) object).booleanValue());
            }
            logger.info("returning...from helper.. cause myComponents container is: " + myComponent.getActualComponent());
            return;
        }
        Component c = (Component) myComponent.getActualComponent();
        if (name.equals("background")) {
        	if(object instanceof String) {
        		logger.info("Setting color: "+object);
        	}
            c.setBackground((Color) object);
        }
        if (name.equals("foreground")) {
            c.setForeground((Color) object);
        }
        if (name.equals("font")) {
            c.setFont((Font) object);
        }
        if (name.equals("border")) {
            if (c instanceof Borderable && object instanceof Border) {
                Borderable b = (Borderable) c;
                b.setBorder((Border) object);
            }
        }

        // if (name.equals("tooltip")) {
        // if (ToolTipSupport.class.isInstance(c)) {
        // ( (Component) c).setToolTipText( (String) object);
        // }
        // }
        if (name.equals("visible")) {
            c.setVisible(((Boolean) object).booleanValue());
        }
        if (name.equals("enabled")) {
            c.setEnabled(((Boolean) object).booleanValue());
        }
        // SHOULD CALL myCOmponent.setComponentValue, I guess.
    }

    public Object getComponentValue(String name) {
        if (!Component.class.isInstance(myComponent.getContainer())) {
            logger.info("Sorry, should not assign a EchoHelper to a non-Echo component");
            return null;
        }
        Component c = (Component) myComponent.getActualComponent();
        // if (name.equals("tooltip")) {
        // if (ToolTipSupport.class.isInstance(c)) {
        // return ( (ToolTipSupport) c).getToolTipText();
        // }
        // }
        if (name.equals("visible")) {
            return Boolean.valueOf(((Component) myComponent.getActualComponent()).isVisible());
        }
        if (name.equals("background")) {
            return c.getBackground();
        }
        if (name.equals("foreground")) {
            return c.getForeground();
        }
        if (name.equals("font")) {
            return c.getFont();
        }
        if (name.equals("enabled")) {
            return Boolean.valueOf(c.isEnabled());
        }
        return null;
    }

    public void deregisterEvent(TipiEvent e) {
        // System.err
        // .println("BEWARE..EVENT IS STILL CONNECTED TO THE COMPONENT!!");
    }

    public void registerEvent(final TipiEvent te) {
    	if(!(myComponent.getActualComponent() instanceof Component)) {
    		return;
    	}
        Component c = (Component) myComponent.getActualComponent();
        if (c == null) {
            logger.info("Cannot register echo event: Container is null!");
            return;
        }
        if (te.isTrigger("onActionPerformed")) {
        }

    }

}
