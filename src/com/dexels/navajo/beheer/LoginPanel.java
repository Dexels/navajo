package com.dexels.navajo.beheer;

import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import java.awt.event.*;

import java.sql.*;
import java.util.ResourceBundle;
//import java.util.Random;
import org.dexels.grus.DbConnectionBroker;
import com.dexels.navajo.document.*;
import java.util.Vector;
//import java.util.Hashtable;
import com.dexels.navajo.util.*;
import com.dexels.navajo.server.*;
import java.net.*;



/**
 * Title:       LoginPanel
 * Description: This is the panel for login.
 * Copyright:    Copyright (c) 2001
 * Company:       dexels
 * @author      Albert Lo
 * @version 1.0
 */

public class LoginPanel extends BaseNavajoPanel {

//  boolean succes=true;

  private BorderLayout borderLayout1 = new BorderLayout();
  private JTextField hostField = new JTextField();
  private JLabel hostLabel = new JLabel();
  private JButton loginButton = new JButton();
  private JComboBox dbmsBox = new JComboBox();
  private JLabel dbmsTypeLabel = new JLabel();
  private JLabel dbmsLabel = new JLabel();
  private JTextField databaseField = new JTextField();
  private JTextField dbUsernameField = new JTextField();
  private JTextField dbPasswordField = new JTextField();
  private JLabel dbmsUserLabel = new JLabel();
  private JLabel dbmsPwdLabel = new JLabel();

  private JPanel centerPanel = new JPanel();
  private GridBagLayout gridBagLayout1 = new GridBagLayout();

  public LoginPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public LoginPanel(RootPanel root) {
    rootPanel= root;
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    title="Navajo Maintainance Logon";
    dbmsBox.addItem("MYSQL");
    dbmsBox.addItem("MSSQL");
    dbmsTypeLabel.setText("DBMS Type");
    dbmsLabel.setText("DBMS");
    dbmsUserLabel.setText("DBMS User");
    dbmsPwdLabel.setText("DBMS Password");

    hostField.setText("localhost");
    hostLabel.setText("Host location");
    loginButton.setText("Login");
    databaseField.setText("navajodemo");
    dbUsernameField.setText("sa");

    loginButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        loginButton_actionPerformed(e);
      }
    });


//    dbUsernameField.setEnabled(true);
//    dbUsernameField.setVisible(true);
//    dbPasswordField.setEnabled(true);
//    dbPasswordField.setVisible(true);

    this.setLayout(borderLayout1);
    centerPanel.setLayout(gridBagLayout1);
    centerPanel.setBackground(Color.yellow);
    this.setBackground(Color.lightGray);

    centerPanel.add(hostField, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(9, 19, 0, 108), 73, -4));
    centerPanel.add(databaseField, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(9, 19, 0, 108), 51, -4));
    centerPanel.add(dbmsBox, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(9, 19, 0, 108), 2, -4));
    centerPanel.add(hostLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(9, 74, 0, 0), 0, 0));
    centerPanel.add(dbmsLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(9, 112, 0, 0), 0, 0));
    centerPanel.add(dbmsTypeLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 83, 0, 0), 0, 0));
    centerPanel.add(dbUsernameField, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(15, 18, 0, 108), 110, -4));
    centerPanel.add(dbPasswordField, new GridBagConstraints(1, 5, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(9, 17, 0, 108), 124, -4));
    centerPanel.add(dbmsUserLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(15, 82, 0, 0), 0, 0));
    centerPanel.add(dbmsPwdLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(9, 53, 0, 0), 0, 0));
    centerPanel.add(loginButton, new GridBagConstraints(0, 6, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 20, 10, 108), 59, 0));

    this.add(centerPanel, BorderLayout.CENTER);
    applyTemplate1();
  }

  void loginButton_actionPerformed(ActionEvent e) {
//    String db="";
    String loginName="";
    String jdbc_driver="";
    String databaseLocation="";
    int dbms_type=0;
    String database="";
    String host="";
//  String name="";
//  String password="";

    String keuze = (String)dbmsBox.getSelectedItem();

    String dbUsername = dbUsernameField.getText();
    String dbPassword = dbPasswordField.getText();

    host=hostField.getText();
    database=databaseField.getText();

    if(keuze.equals("MSSQL")){
      jdbc_driver="com.inet.tds.TdsDriver";
      dbms_type=Authorisation.DBMS_MSSQL;
      databaseLocation="jdbc:inetdae7:"+host+":1433?database="+database;
    }
    else if(keuze.equals("MYSQL")){
      jdbc_driver="org.gjt.mm.mysql.Driver";
      dbms_type=Authorisation.DBMS_MYSQL;
      databaseLocation="jdbc:mysql://";
      databaseLocation=databaseLocation+host+"/"+database;
    }

//    name      = nameField.getText();
//    password  = new String(passwordField.getPassword());

    try{
      rootPanel.auth = new Authorisation(dbms_type);
      System.err.println("dbUsername= " +  dbUsername);
      System.err.println("dbPassword= " + dbPassword);
      DbConnectionBroker myBroker = new DbConnectionBroker(jdbc_driver, databaseLocation, dbUsername, dbPassword, 2, 25, "/tmp/log.db", 0.1);


      Util.debugLog("Created pool:");
//      rootPanel.access = new Access(78, 1, 2, "arjen", "password", "", host, "piupohost", rootPanel.myBroker, dbms_type);
      rootPanel.access = new Access(78, 1, 2, "", "", "", host, "localhost", myBroker, dbms_type);
      Util.debugLog("Access = " + rootPanel.access);
      rootPanel.auth.logAction(rootPanel.access, 3, "Geen commentaar");
      Util.debugLog("created logaction");
    }
    catch(NullPointerException nEx){
      nEx.printStackTrace(System.out);
      error=true;
      errorField.setForeground(Color.red);
      errorField.setText("cannot find database");
    }
    catch(Exception ex){
      ex.printStackTrace(System.out);
      error=true;
      errorField.setForeground(Color.red);
      errorField.setText("login failed");
    }

    if(!error){
      BeheerPanel bp = new BeheerPanel(rootPanel);
      rootPanel.changeContentPane(bp);
    }
    error=false;
  }



}
