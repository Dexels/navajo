package com.dexels.navajo.tipi.components.swingimpl.springbutton;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.*;

import javax.swing.*;

/**
 *
 * @author Frank
 */
public class SpringButton extends JButton {
//    private List<ActionListener> myActionListeners = new ArrayList<ActionListener>();
   
    public SpringButton(String label) {
    	this();
    	setText(label);
    }
    public SpringButton() {

        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               fireSpring();
            } 
        });
    } 

   public void fireSpring() {
         SpringGlassPane  glassPane = new SpringGlassPane();
        ((RootPaneContainer)getTopLevelAncestor()).getRootPane().setGlassPane(glassPane);
        
        SpringGlassPane cc = (SpringGlassPane) ((RootPaneContainer)getTopLevelAncestor()).getGlassPane();
        cc.setVisible(true);
        System.err.println("My bounds: "+getTopLevelAncestor());
        BufferedImage bi = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
        boolean bb = isBorderPainted();
        setBorderPainted(false);
        paintComponent(bi.getGraphics());
        setBorderPainted(bb);
        Rectangle bounds = getBounds();
        Point location = new Point(0,0);
        location = SwingUtilities.convertPoint(this, location, ((RootPaneContainer)getTopLevelAncestor()).getRootPane());
        bounds.setLocation(location);
        cc.showSpring(bounds,bi);
        
   }
   
   public void setIconUrl(URL u) {
		setIcon(new ImageIcon(u));
	}
   
//   public void addActionListener(ActionListener a) {
//       myActionListeners.add(a);
//   }
//
//   public void removeActionListener(ActionListener a) {
//       myActionListeners.remove(a);
//   }

}

