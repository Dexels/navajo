package com.dexels.navajo.tipi.components.swingimpl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

public class TipiPopupMenu
    extends TipiSwingComponentImpl {
  private JPopupMenu myMenu;
//  public void removeFromContainer(Object c) {
//    myMenu.remove( (Component) c);
//  }
  public Object createContainer() {
    myMenu = new JPopupMenu();
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    addHelper(th);
    System.err.println("Created popup");
    return myMenu;
  }
  
  public void showPopup(MouseEvent e) {
		if (myMenu.isShowing()) {
			myMenu.show(getSwingContainer(), e.getX(), e.getY());
		}
	}

  
public void	addedToParentContainer(Object parentContainer, Object container, Object constraint) {
	System.err.println("Added to: "+parentContainer);
	if(parentContainer instanceof Component) {
		Component c = (Component)parentContainer;
		c.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e) {
				if(e.isPopupTrigger()) {
					showPopup(e);
				}
			}

			public void mousePressed(MouseEvent e) {
				if(e.isPopupTrigger()) {
					showPopup(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()) {
					showPopup(e);
				}
			}});
	}
}

  public Object getContainer() {
    return myMenu;
  }

  public void addToContainer(Component menu, Object item) {
	  
	  myMenu.add(menu);
	  System.err.println("\n\nAdding: "+menu+" to: "+getContainer());
  }
}
