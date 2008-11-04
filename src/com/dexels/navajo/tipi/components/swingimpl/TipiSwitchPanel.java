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
	private final Map<Object,String> componentMap = new HashMap<Object,String>();
    private String selectedId;
    private Integer selectedIndex;
	public TipiSwitchPanel() {
	}

	public Object createContainer() {
        Container c = (Container)super.createContainer();
        cardLayout = new CardLayout();
        c.setLayout(cardLayout);
        return c;
	}

	@Override
	 public void addComponent(TipiComponent c, int index, TipiContext context, Object td) {
         System.err.println("Trapped an addcomponent!");
        if (c.getContainer()!=null) {
            componentMap.put(c.getContainer(), c.getId());
        }
        super.addComponent(c, index, context, td);
    }

	@Override

	public void addToContainer(final Object c, final Object constraints) {
          final String name = componentMap.get(c);
          runSyncInEventThread(new Runnable(){

			public void run() {
		          System.err.println("Adding to switch with name: "+name);
		          if (name==null) {
		              getSwingContainer().add( (Component) c, name);
		              if (getChildCount()<=1) {
		                  selectedIndex = new Integer(0);
//		                  cardLayout.show(myPanel,name);
//						System.err.println("Showing component: "+name);
					}
		          } else {
		        	  System.err.println("Component: "+c+" not found");
		              getSwingContainer().add( (Component) c, name);
		          }
		          updateSelected();
			}});
   
        }
	 
	public void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
		if (name.equals("selectedId")) {
		    selectedId = (String) object;
            selectedIndex = null;
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
        	System.err.println("Switchin 2: "+selectedId);
            tc = getTipiComponent(selectedId);
        	System.err.println("Switchin... "+tc.getPath());

        } 
        
        if (tc==null) {
            System.err.println("Oh @#$#@$ couldnt find: "+selectedId);
            return;
        }
        final TipiComponent tc2 = tc;
        runSyncInEventThread(new Runnable(){

			public void run() {
			    cardLayout.show(getSwingContainer(), tc2.getId());
			    
			}});
          
    }
}
