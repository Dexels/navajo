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

public class DefaultTipiScreen extends TipiPanel implements TipiScreen{

  private Map tipiMap = new HashMap();

  public DefaultTipiScreen() {
    //setBackground(Color.darkGray);
  }

  public void load(XMLElement elm, TipiContext context) throws com.dexels.navajo.tipi.TipiException {
    String elmName = elm.getName();
    if(!elmName.equals("screen")){
      throw new TipiException("Screen node not found!, found " + elmName + " instead.");
    }
  }

  public void addComponent(TipiComponent c, TipiContext context, Map td){
    this.add((JComponent)c, td);
  }

//  public void addTipi(Tipi t, TipiContext context) {
//    addComponent(t, context);
  public void addTipi(Tipi t, TipiContext context, Map td) {
    addComponent(t, context, td);
    tipiMap.put(t.getName(),t);
  }

  public Tipi getTipi(String name) {
    System.err.println("Lookin for tipi: "+name);
    System.err.println("IN: "+tipiMap.toString());
    return (Tipi)tipiMap.get(name);
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
    Tipi t = getTipi(name);
    if (t==null) {
      return null;
    }
    /** @todo Add support for nested tipis */
    return t.getContainerByPath(rest);

  }
  public Tipi getTipiByPath(String path) {
    System.err.println("getTipiByPath (Screen: ): "+path);
    int s = path.indexOf("/");
    if (s==-1) {
      return getTipi(path);
    }
    if (s==0) {
      return getTipiByPath(path.substring(1));
    }

    String name = path.substring(0,s);
    String rest = path.substring(s);
    System.err.println("Name: "+name);
    System.err.println("Rest: "+rest);
    Tipi t = getTipi(name);
    if (t==null) {
      return null;
    }
    /** @todo Add support for nested tipis */
    return t.getTipiByPath(rest);

  }

}