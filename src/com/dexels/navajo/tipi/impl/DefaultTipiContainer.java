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

  public void load(XMLElement elm, XMLElement instance, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    TipiPanel myPanel = new TipiPanel();
    prefix = (String) instance.getAttribute("prefix");
    String b = (String) instance.getAttribute("border");
    if(b != null && b.equals("true")){
      myPanel.addBorder();
    }
    setContainer(myPanel);

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
}