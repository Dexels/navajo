package com.dexels.navajo.tipi.impl;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.*;
import nanoxml.*;
import com.dexels.navajo.document.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultTipi extends TipiPanel implements Tipi{

  private String myService = "";
  private String myName = "";
  private Navajo myNavajo = null;
  public DefaultTipi() {
    setBackground(Color.white);
  }

  public void load(XMLElement elm, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    myName = (String)elm.getAttribute("name");
    myService = (String)elm.getAttribute("service");
  }


  public Navajo getNavajo() {
    return myNavajo;
  }

  public void addProperty(String name, TipiComponent dummy){
    // Dummy
  }


  public void performService(TipiContext context) {
    if (myNavajo==null) {
      myNavajo = new Navajo();
    }
    context.performTipiMethod(this,myService);
  }

  public void loadData(Navajo n) {
    System.err.println(">>>>>>>>>>>>>... "+n.toXml().toString());
  }

  public void addComponent(TipiComponent c){
    this.add((JComponent)c);
  }
  public void addTipi(Tipi t) {
  }
  public void addTipiContainer(TipiContainer t) {
  }
}