package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class DefaultTipiContainer
    extends TipiComponent
    implements TipiContainer {

//  private TipiPanel myPanel = new TipiPanel();
  protected ArrayList containerList = new ArrayList();
  protected String prefix;
  protected String myName;
  protected Map containerMap = new HashMap();
  protected TipiPopupMenu myPopupMenu = null;

//  public DefaultTipiContainer() {
//  }

  public void load(XMLElement elm, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    TipiPanel myPanel = new TipiPanel();
    setContainer(myPanel);
    prefix = (String) elm.getAttribute("prefix");
    myName = (String) elm.getAttribute("name");
    String popup = (String) elm.getAttribute("popup");
    if (popup != null) {
//      myPopupMenu = new TipiPopupMenu();
      myPopupMenu = context.instantiateTipiPopupMenu(popup);
      getContainer().addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          if (e.isPopupTrigger()) {
            showPopup(e);
          }
        }

        public void mouseReleased(MouseEvent e) {
          if (e.isPopupTrigger()) {
            showPopup(e);
          }
        }
      });

    }
  }

  public void showPopup(MouseEvent e) {
    myPopupMenu.show(getContainer(), e.getX(), e.getY());
  }

  public String getName() {
    return myName;
  }

  public void addTipiContainer(TipiContainer t, TipiContext context, Map td) {
    containerList.add(t);
    containerMap.put(t.getName(), t);
    addComponent(t, context, td);
  }

  public void loadData(Navajo n, TipiContext context) throws TipiException {

    if (n == null) {
      return;
    }
    for (int i = 0; i < containerList.size(); i++) {
      TipiContainer current = (TipiContainer) containerList.get(i);
      current.loadData(n, context);
    }
    for (int i = 0; i < properties.size(); i++) {
      BasePropertyComponent current = (BasePropertyComponent) properties.get(i);
      Property p;
      System.err.println("MY PROP: "+prefix + "/" + (String) propertyNames.get(i));
      if (prefix != null) {
        p = n.getRootMessage().getPropertyByPath(prefix + "/" + (String) propertyNames.get(i));
      }
      else {
        p = n.getRootMessage().getPropertyByPath( (String) propertyNames.get(i));
      }
      current.setProperty(p);
    }
  }
//  public TipiContainer getContainerByPath(String path) {
//    int s = path.indexOf("/");
//    if (s==-1) {
//      throw new RuntimeException("Can not retrieve container from screen!");
//    }
//    if (s==0) {
//      return getContainerByPath(path.substring(1));
//    }
//
//    String name = path.substring(0,s);
//    String rest = path.substring(s);
//    System.err.println("Name: "+name);
//    System.err.println("Rest: "+rest);
//    TipiContainer t = getTipiContainer(name);
//    if (t==null) {
//      return null;
//    }
//    /** @todo Add support for nested tipis */
//    return t.getContainerByPath(rest);
//  }

}