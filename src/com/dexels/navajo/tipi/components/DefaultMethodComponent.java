package com.dexels.navajo.tipi.components;
import nanoxml.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.*;
import java.awt.event.*;

import javax.swing.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultMethodComponent extends TipiButton implements MethodComponent {
  private String name = "";
  private String label = "";
  private Navajo myNavajo = null;
  private TipiContext myContext = null;
  private Tipi myTipi;
  public DefaultMethodComponent() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void load(XMLElement elm, TipiComponent tc, TipiContext context) {
    name = (String)elm.getAttribute("name");
    label = (String)elm.getAttribute("label");
    myTipi = (Tipi)tc;
    myTipi.addMethod(this);
    if (label==null) {
      setText(name);
    } else {
      setText(label);
    }
  }

  public void loadData(Navajo n, TipiContext tc) {
    System.err.println("DEFAULTMETHOD: LOADING DATA: "+n.toXml().toString());
   myNavajo = n;
   myContext = tc;
  }

  public void addComponent(TipiComponent c, TipiContext context) {
  }
  public void addProperty(String name, TipiComponent comp, TipiContext context) {
  }
  public void addTipi(Tipi t, TipiContext context) {
  }
  public void addTipiContainer(TipiContainer t, TipiContext context) {
  }
  private void jbInit() throws Exception {
    this.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        this_actionPerformed(e);
      }
    });
  }

  void this_actionPerformed(ActionEvent e) {
    if (myNavajo==null) {
      JOptionPane.showMessageDialog(this,"No navajo!","",JOptionPane.ERROR_MESSAGE);
    } else {
//      myContext.p
      System.err.println("DEFAULTMETHOD: LOADING DATA: "+myNavajo.toXml().toString());
      myContext.performTipiMethod(myTipi,name);
    }

  }
}