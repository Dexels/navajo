package com.dexels.navajo.tipi.impl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import tipi.MainApplication;

public class DefaultTipiDesktop extends DefaultTipi {

  public Container createContainer() {
    LogoDeskTop jp = new LogoDeskTop();
    jp.setDragMode(JDesktopPane.LIVE_DRAG_MODE);
    return jp;
  }

  public void addToContainer(Component c, Object constraints) {
    //System.err.println("ADDING COMPONENT TO DESKTOP");
    getContainer().add(c,0);
  }

  public void removeFromContainer(Component c) {
    System.err.println("Removing from desktop!!>>> "+c==null?"Nada":c.getClass().toString());
    getContainer().remove(c);
    getContainer().repaint();
  }
  public DefaultTipiDesktop() {
    initContainer();
  }

//  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
//    super.load(definition,instance,context);
//  }

  public void setComponentValue(String name, Object value){
    super.setComponentValue(name, value);
    if("logo".equals(name)){
      System.err.println("Found logo: " + (String)value);
      ImageIcon im = new ImageIcon(MainApplication.class.getResource((String)value));
      if(im != null){
        ((LogoDeskTop)getContainer()).setImage(im.getImage());
      }
    }
  }

}