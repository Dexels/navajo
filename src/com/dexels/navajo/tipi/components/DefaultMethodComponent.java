package com.dexels.navajo.tipi.components;
import nanoxml.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.swingclient.components.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultMethodComponent extends TipiComponent implements MethodComponent {
  private BaseButton myButton = new BaseButton();
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

  public Container getContainer() {
    return myButton;
  }
  public void load(XMLElement elm, TipiContext context) {
    throw new RuntimeException("Dont use this one!");
  }
  public void load(XMLElement elm, TipiComponent tc, TipiContext context) {
    name = (String)elm.getAttribute("name");
    label = (String)elm.getAttribute("label");
    myTipi = (Tipi)tc;
    myTipi.addMethod(this);
    if (label==null) {
      myButton.setText(name);
    } else {
      myButton.setText(label);
    }
  }

  public void loadData(Navajo n, TipiContext tc) {
   myNavajo = n;
   myContext = tc;
  }

  private void jbInit() throws Exception {
    myButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        this_actionPerformed(e);
      }
    });
  }

  void this_actionPerformed(ActionEvent e) {
    if (myNavajo==null) {
      JOptionPane.showMessageDialog(myButton,"No navajo!","",JOptionPane.ERROR_MESSAGE);
    } else {
//      myContext.p
//      System.err.println("DEFAULTMETHOD: LOADING DATA: "+myNavajo.toXml().toString());
      try {
        myContext.performTipiMethod(myTipi,name);
      }
      catch (Exception ex) {
        System.err.println("Error while performing method!");
        ex.printStackTrace();
      }

    }

  }
}