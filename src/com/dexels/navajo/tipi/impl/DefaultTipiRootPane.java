package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class DefaultTipiRootPane extends DefaultTipi {

  protected RootPaneContainer myRootPaneContainer;

  protected abstract void setBounds(Rectangle r);
  protected abstract Rectangle getBounds();
  protected abstract void setIcon(ImageIcon ic);
  protected abstract void setTitle(String s);
  protected abstract void setJMenuBar(JMenuBar s);
//  public DefaultTipiRootPane() {
//  }


//  public void load(XMLElement definition, XMLElement instance, TipiContext context) throws TipiException {
//    super.load(definition,instance,context);
//
//
//    String elmName = definition.getName();
//
//    String menubar = (String)definition.getAttribute("menubar");
//    if (menubar!=null) {
//      XMLElement xe = context.getTipiMenubarDefinition(menubar);
//      TipiMenubar tm = context.createTipiMenubar();
//      tm.load(xe,context);
//      context.getTopLevel().setTipiMenubar(tm);
//    }
//  }

  public void setComponentValue(String name, Object object) {
    super.setComponentValue(name,object);
//    JInternalFrame jj = (JInternalFrame)getContainer();
    Rectangle r = getBounds();
    if (name.equals("menubar")) {
      System.err.println("MENUBAR!!!!");
      try {
        XMLElement xe = myContext.getTipiMenubarDefinition( (String) object);
        TipiMenubar tm = myContext.createTipiMenubar();
        tm.load(xe, myContext);
        setJMenuBar(tm);
//        System.err.println("Cound: "+tm.getMenuCount());
      }
      catch (TipiException ex) {
        ex.printStackTrace();
      }
//      myContext.getTopLevel().setTipiMenubar(tm);
    }
    if (name.equals("x")) {
      r.x = Integer.parseInt( (String) object);
    }
    if (name.equals("y")) {
     r.y = Integer.parseInt( (String) object);
   }
   if (name.equals("w")) {
     r.width = Integer.parseInt( (String) object);
   }
   if (name.equals("h")) {
     r.height = Integer.parseInt( (String) object);
   }
   if (name.equals("title")) {
    setTitle((String)object);
  }
  if (name.equals("icon")) {
    String icon = (String)object;
    ImageIcon ic = myContext.getIcon(icon);
    setIcon(ic);
  }
   setBounds(r);
  }

}