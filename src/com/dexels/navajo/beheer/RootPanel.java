package com.dexels.navajo.beheer;

import java.awt.*;
import com.borland.jbcl.layout.*;


import java.sql.*;
import java.util.ResourceBundle;
//import java.util.Random;
import org.dexels.grus.DbConnectionBroker;
import com.dexels.navajo.document.*;
import java.util.Vector;
//import java.util.Hashtable;
import com.dexels.navajo.util.*;
import com.dexels.navajo.server.*;
import javax.swing.*;


/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class RootPanel extends JPanel {

  public Authorisation auth=null;
//  public DbConnectionBroker myBroker=null;
  public Access access=null;

  private   BorderLayout borderLayout1 = new BorderLayout();
  private   JPanel centerPanel = new JPanel();
  private   JPanel jPanel1 = new JPanel();
  private   JPanel jPanel2 = new JPanel();
  private   JPanel jPanel3 = new JPanel();
  private   JPanel jPanel4 = new JPanel();

  public RootPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }


  void jbInit() throws Exception {
    this.setLayout(borderLayout1);

    centerPanel = new LoginPanel(this);
//    centerPanel.setLayout(new XYLayout());
    jPanel4.setBackground(Color.yellow);
    jPanel1.setBackground(Color.yellow);
    jPanel2.setBackground(Color.yellow);
    jPanel3.setBackground(Color.yellow);
    this.add(centerPanel, BorderLayout.CENTER);
    this.add(jPanel1, BorderLayout.EAST);
    this.add(jPanel2, BorderLayout.WEST);
    this.add(jPanel3, BorderLayout.NORTH);
    this.add(jPanel4, BorderLayout.SOUTH);

  }


  public void changeContentPane(BaseNavajoPanel p) {
    this.remove(centerPanel);
    centerPanel=p;
    this.add(centerPanel, BorderLayout.CENTER);
    this.revalidate();
  }

  public void changeToBeheerPanel() {
    BeheerPanel p = new BeheerPanel(this);
    changeContentPane(p);
  }

  public void logout(){
    auth=null;
//    myBroker=null;
    access=null;
  }
}
