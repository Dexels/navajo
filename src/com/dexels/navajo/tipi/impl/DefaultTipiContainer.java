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

public class DefaultTipiContainer extends TipiComponent implements TipiContainer{

//  private TipiPanel myPanel = new TipiPanel();
  protected ArrayList containerList = new ArrayList();
  protected String prefix;
  protected String myName;
  protected Map containerMap = new HashMap();
  protected TipiPopupMenu myPopupMenu = null;

  public DefaultTipiContainer() {
//    myPanel.setBackground(Color.blue);
 //   setPreferredSize(new Dimension(100,50));
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void load(XMLElement elm, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    TipiPanel myPanel = new TipiPanel();
    setContainer(myPanel);
    prefix = (String) elm.getAttribute("prefix");
    myName = (String) elm.getAttribute("name");
    String popup = (String) elm.getAttribute("popup");
    if (popup!=null) {
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
    myPopupMenu.show(getContainer(),e.getX(),e.getY());
  }

  public String getName() {
    return myName;
  }

  public void addTipiContainer(TipiContainer t, TipiContext context, Map td) {
      containerList.add(t);
      containerMap.put(t.getName(),t);
      addComponent(t, context, td);
  }

  public TipiContainer getTipiContainer(String s) {
    return (TipiContainer)containerMap.get(s);
  }

  public TipiContainer getTipiContainer(int t) {
    return (TipiContainer)containerList.get(t);
  }

  public int getTipiContainerCount() {
    return containerMap.size();
  }

  public void loadData(Navajo n, TipiContext context) {
     for (int i = 0; i < containerList.size(); i++) {
      TipiContainer current = (TipiContainer)containerList.get(i);
      current.loadData(n,context);
    }
    for (int i = 0; i < properties.size(); i++) {
      BasePropertyComponent current = (BasePropertyComponent)properties.get(i);
      Property p;
      if(prefix != null){
        p = n.getRootMessage().getPropertyByPath(prefix + "/" + (String)propertyNames.get(i));
      }else{
        p = n.getRootMessage().getPropertyByPath((String)propertyNames.get(i));
      }
      current.setProperty(p);
    }
  }
  private void jbInit() throws Exception {
//    getContainer().setBorder(BorderFactory.createEtchedBorder());
  }
  public TipiContainer getContainerByPath(String path) {
    int s = path.indexOf("/");
    if (s==-1) {
      throw new RuntimeException("Can not retrieve container from screen!");
    }
    if (s==0) {
      return getContainerByPath(path.substring(1));
    }

    String name = path.substring(0,s);
    String rest = path.substring(s);
    System.err.println("Name: "+name);
    System.err.println("Rest: "+rest);
    TipiContainer t = getTipiContainer(name);
    if (t==null) {
      return null;
    }
    /** @todo Add support for nested tipis */
    return t.getContainerByPath(rest);
  }

}