package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import javax.swing.*;
import java.awt.*;
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

public class DefaultTipiContainer extends TipiPanel implements TipiContainer{

  private ArrayList propertyNames = new ArrayList();
  private ArrayList properties = new ArrayList();
  private ArrayList containerList = new ArrayList();
  private String prefix;
  private Map containerMap = new HashMap();

  public DefaultTipiContainer() {
    setBackground(Color.blue);
 //   setPreferredSize(new Dimension(100,50));
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void load(XMLElement elm, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    prefix = (String) elm.getAttribute("prefix");
  }


  public void addComponent(TipiComponent c, TipiContext context, Map td){
    this.add((JComponent)c, td);
  }

  public void addProperty(String name, TipiComponent comp, TipiContext context, Map td){
    propertyNames.add(name);
    properties.add(comp);
    addComponent(comp, context, td);
  }

  public void addTipiContainer(TipiContainer t, TipiContext context, Map td) {
      containerList.add(t);
      containerMap.put(t.getName(),t);
      addComponent(t, context, td);
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
    this.setBorder(BorderFactory.createEtchedBorder());
  }
  public TipiContainer getContainerByPath(String path) {
    return null;
  }

}