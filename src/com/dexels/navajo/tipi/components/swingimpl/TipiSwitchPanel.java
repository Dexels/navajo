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

    private String selectedId;

    private Integer selectedIndex;
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
         System.err.println("Trapped an addcomponent!");
        if (c.getContainer()!=null) {
            componentMap.put(c.getContainer(), c.getId());
        }
        super.addComponent(c, index, context, td);
    }
      public void addToContainer(final Object c, final Object constraints) {
          String name = (String)componentMap.get(c);
          if (name==null) {
              getSwingContainer().add( (Component) c, name);
              if (getChildCount()<=1) {
                  selectedIndex = new Integer(0);
//                  cardLayout.show(myPanel,name);
//				System.err.println("Showing component: "+name);
			}
          } else {
        	  System.err.println("Component: "+c+" not found");
          }
          updateSelected();
        }
	 
	public void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
		if (name.equals("selected")) {
		    selectedId = (String) object;
            selectedIndex = null;
 		}
		if (name.equals("selectedindex")) {
			selectedIndex = (Integer) object;
            selectedId = null;
		}
		updateSelected();
        /** @todo Override this com.dexels.navajo.tipi.TipiComponent method */
	}
    
    private void updateSelected() {
        
        TipiComponent tc = null;
        
        if (selectedIndex!=null) {
            tc = getTipiComponent(selectedIndex.intValue());
        } 
        if (selectedId!=null) {
            tc = getTipiComponent(selectedId);
        } 
        
        if (tc==null) {
            System.err.println("Oh @#$#@$");
            return;
        }
        cardLayout.show(myPanel, tc.getId());
        
    }
}
