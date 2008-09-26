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


public class TipiPopupMenu
    extends TipiSwingDataComponentImpl {
//  private JPopupMenu myMenu;
//  public void removeFromContainer(Object c) {
//    myMenu.remove( (Component) c);
//  }
  public Object createContainer() {
	  JPopupMenu.setDefaultLightWeightPopupEnabled(false);
	  	  JPopupMenu myMenu = new JPopupMenu();
//	  myMenu.set
//    TipiHelper th = new TipiSwingHelper();
//    th.initHelper(this);
//    addHelper(th);
  System.err.println("Created popup");
    return myMenu;
  }
  
  @Override
  public void showPopup(MouseEvent e) {
	  JPopupMenu jp = (JPopupMenu)getContainer();
	  Point point = e.getPoint();
	  System.err.println("SOURCE: "+e.getSource());
//	  Thread.dumpStack();
		if (jp.isShowing()) {
			System.err.println("one");
				jp.show((Component)e.getSource(), point.x,point.y);
		} else {
			System.err.println("two");
			 SwingUtilities.convertPointToScreen(point,(Component)e.getSource());
			 jp.show((Component)e.getSource(), point.x,point.y);
			 jp.setLocation(point);
		}
		 
	}

  
public void	addedToParentContainer(Object parentContainer, Object container, Object constraint) {
	if(parentContainer instanceof Component) {
		Component c = (Component)parentContainer;
		c.addMouseListener(new MouseAdapter(){

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
//
//  public Object getContainer() {
//    return myMenu;
//  }

//  public void addToContainer(final Object c, final Object constraints) {
//	  System.err.println("\n\nAdding: "+c+" to: "+getContainer());
//
//	  getSwingContainer().add((Component)c);
//  }


public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
	final JFrame jd = new JFrame("aap");
	jd.getContentPane().add(new JDesktopPane());
	final JPopupMenu jp = new JPopupMenu("Hoei");
	jp.add(new JMenuItem("monekyyy"));
	jp.add(new JMenuItem("monekyyy"));
	jp.add(new JMenuItem("monekyyy"));
	jp.add(new JMenuItem("monekyyy"));
	jp.add(new JMenuItem("monekyyy"));
	jp.add(new JMenuItem("monekyyy"));
	jd.setSize(200,150);


	UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");


	jd.setVisible(true);
	jd.getContentPane().addMouseListener(new MouseListener(){

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
			
		}

		public void mousePressed(MouseEvent e) {
			if(e.isPopupTrigger()) {
				jp.show(jd.getContentPane(), e.getX(), e.getY());
			}
	}

		public void mouseReleased(MouseEvent e) {
			if(e.isPopupTrigger()) {
				jp.show(jd.getContentPane(), e.getX(), e.getY());
			}
		
		}
		
	});
}

}
