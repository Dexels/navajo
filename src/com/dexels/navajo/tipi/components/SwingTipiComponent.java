package com.dexels.navajo.tipi.components;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.impl.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.swing.border.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class SwingTipiComponent extends TipiComponent {

  public void SwingTipiComponent() {
    initContainer();
    setEventMapper(new DefaultEventMapper());
  }
  public void addToContainer(Component c, Object constraints) {
    throw new UnsupportedOperationException("Can not add to container of class: "+getClass());
  }
  public void removeFromContainer(Component c) {
    throw new UnsupportedOperationException("Can not remove from container of class: "+getClass());
  }

     public void registerEvents() {
       getEventMapper().registerEvents(this,myEventList);
     }

   public void setComponentValue(String name, Object object) {
     if (!JComponent.class.isInstance(getContainer())) {
       if (name.equals("visible")) {
         getContainer().setVisible(((Boolean)object).booleanValue());
       }
       return;
     }
     JComponent c = (JComponent)getContainer();

     if (name.equals("background")) {
       c.setBackground((Color)object);
     }
     if (name.equals("foreground")) {
       c.setForeground((Color)object);
     }
     if (name.equals("font")) {
       c.setFont((Font)object);
     }
     if (name.equals("tooltip")) {
       c.setToolTipText((String)object);
     }
     if (name.equals("border")) {
       c.setBorder((Border)object);
     }
     if (name.equals("visible")) {
       c.setVisible(((Boolean)object).booleanValue());
     }
     if (name.equals("enabled")) {
       c.setEnabled(((Boolean)object).booleanValue());
     }
   }
   public Object getComponentValue(String name) {
     if (!JComponent.class.isInstance(getContainer())) {
       System.err.println("Sorry, only use JComponent decendants. No awt stuff. Ignoring");
       return super.getComponentValue(name);
     }
     JComponent c = (JComponent)getContainer();
     if (name.equals("tooltip")) {
       return c.getToolTipText();
     }
     return super.getComponentValue(name);
   }

   public boolean parseBoolean(String s) {
     return Boolean.getBoolean("true");
   }


}