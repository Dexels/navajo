package com.dexels.navajo.tipi.components;

import javax.swing.*;
import com.dexels.navajo.document.*;
import java.util.*;
import java.awt.event.*;
import com.dexels.navajo.tipi.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DefaultMethodToolBar extends JToolBar {
  private Navajo myNavajo = null;
  private Tipi myTipi = null;
  private TipiContext myContext = null;
  public DefaultMethodToolBar() {
//    add(new JButton("hopla!"));
  }

  public void load(Tipi t, Navajo n, TipiContext c) {

    System.err.println("LOADING METHODBAR!");
    myNavajo = n;
    myTipi = t;
    myContext = c;
    ArrayList al = n.getAllMethods();
    for (int i = 0; i < al.size(); i++) {
      Method m = (Method)al.get(i);
      addButton(m);
      System.err.println("ADDING BUTTON: "+m.getName());
    }

  }

  public void addButton(Method m) {
    JButton jb = new JButton();
    jb.setName(m.getName());
    jb.setText(m.getName());
    jb.setActionCommand(m.getName());
    jb.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        buttonPressed(e);
      }
    });
    add(jb);
  }

  public void buttonPressed(ActionEvent e) {
    try {
      String methodName = e.getActionCommand();
      myTipi.performService(myContext,methodName);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}