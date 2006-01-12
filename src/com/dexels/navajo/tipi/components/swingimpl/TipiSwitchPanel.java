package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.util.*;

import com.dexels.navajo.tipi.*;


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

public class TipiSwitchPanel extends TipiPanel {

    private CardLayout cardLayout;

	private final Map componentMap = new HashMap();
	public TipiSwitchPanel() {
	}

	public Object createContainer() {
//		myContainer = new Grid(1);
        Container c = (Container)super.createContainer();
        cardLayout = new CardLayout();
        c.setLayout(cardLayout);
        return c;
	}

	 public void addComponent(TipiComponent c, int index, TipiContext context, Object td) {
        super.addComponent(c, index, context, td);
        System.err.println("Trapped an addcomponent!");
        if (c.getContainer()!=null) {
            componentMap.put(c.getContainer(), c.getId());
        }
     }

      public void addToContainer(final Object c, final Object constraints) {
          String name = (String)componentMap.get(c);
          getSwingContainer().add( (Component) c, name);
        }
	 
	public void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
		if (name.equals("selected")) {
//			hideAll();
			String sel = (String) object;
            TipiComponent tc = getTipiComponent(sel);
            if (tc==null) {
                System.err.println("Oh @#$#@$");
                return;
            }
            cardLayout.show(myPanel, tc.getId());
 		}
		if (name.equals("selectedindex")) {
			final Integer sel = (Integer) object;
           TipiComponent tc = getTipiComponent(sel.intValue());
            if (tc==null) {
                System.err.println("Oh @#$#@$");
                return;
            }
            cardLayout.show(myPanel, tc.getId());
		}
		/** @todo Override this com.dexels.navajo.tipi.TipiComponent method */
	}
}
