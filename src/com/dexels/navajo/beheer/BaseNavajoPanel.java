package com.dexels.navajo.beheer;

import java.awt.*;

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
import com.borland.jbcl.layout.*;
import java.awt.event.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

 public class BaseNavajoPanel extends JPanel{
//  BeheerFrame rootFrame = null;
  RootPanel rootPanel = null;
  boolean error=false;
  String title="A Title";
  boolean newEntry=false;

//  int width=570;
//  int height=370;


//  Authorisation auth=null;
//  DbConnectionBroker myBroker=null;
//  Access access=null;

//  XYLayout templateLayout = new XYLayout();
  JLabel titleField = new JLabel();

  JButton cancelButton = new JButton();
  JButton okButton = new JButton();
  JButton deleteButton = new JButton();
  JTextField errorField = new JTextField();

  protected JPanel headerPanel = new JPanel();
  protected JPanel footerPanel = new JPanel();
  protected JPanel errorPanel = new JPanel();
  protected JPanel bottonsPanel = new JPanel();

  public BaseNavajoPanel() {
  }


  public BaseNavajoPanel(RootPanel root) {
    rootPanel = root;
  }

  void jbInit() throws Exception {
  }


  /**
   * @todo wel of niet okButton standaard op disable zetten bij newEntry?
   */
  protected void applyTemplate2(){

    //this is for the screens to edit data
    applyTemplate1();
//template 2
    bottonsPanel.setLayout(new BorderLayout());
    bottonsPanel.setBackground(Color.yellow);
    //this is for the screens to edit data
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelButton_actionPerformed(e);
      }
    });
    cancelButton.setText("Cancel");
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okButton_actionPerformed(e);
      }
    });
    okButton.setText("Ok");

    if(!newEntry){
      deleteButton.setForeground(Color.red);
      deleteButton.setText("Delete");
      deleteButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          deleteButton_actionPerformed(e);
        }
      });
      bottonsPanel.add(deleteButton, BorderLayout.WEST);
    }

    JPanel ok_cancelPanel = new JPanel();
    ok_cancelPanel.setLayout(new XYLayout());
    ok_cancelPanel.setBackground(Color.yellow);
    ok_cancelPanel.setPreferredSize(new Dimension(150, 27));
    ok_cancelPanel.add(okButton, new XYConstraints(0, 0, 73, -1));
    ok_cancelPanel.add(cancelButton, new XYConstraints(74, 0, -1, -1));
    bottonsPanel.add(ok_cancelPanel, BorderLayout.EAST);
    footerPanel.add(bottonsPanel, null);
  }

  protected void applyTemplate1(){
//----    template 1
    footerPanel.setBackground(Color.yellow);
    footerPanel.setPreferredSize(new Dimension(70, 70));
    footerPanel.setLayout(new VerticalFlowLayout());

    headerPanel.setBackground(Color.yellow
    );
    headerPanel.setPreferredSize(new Dimension(50, 50));
    headerPanel.setLayout(new XYLayout());
    headerPanel.add(titleField, new XYConstraints(12, 5, 370, 42));

    this.add(headerPanel, BorderLayout.NORTH);
    this.add(footerPanel, BorderLayout.SOUTH);

    titleField.setHorizontalTextPosition(SwingConstants.CENTER);
    titleField.setFont(new java.awt.Font("SansSerif", 3, 22));
    titleField.setForeground(Color.blue);
//    titleField.setText("AAAP");
    titleField.setText(title);

    errorField.setEditable(false);
    errorField.setVisible(false);
    errorField.setBackground(Color.yellow);
    errorField.setForeground(Color.red);
    errorPanel.setLayout(new GridLayout());
    errorPanel.setBackground(Color.yellow);
    errorPanel.add(errorField, null);
    footerPanel.add(errorPanel, null);

  }



  void cancelButton_actionPerformed(ActionEvent e) {
  }
  void okButton_actionPerformed(ActionEvent e) {
  }
  void deleteButton_actionPerformed(ActionEvent e) {
  }

  void errorWithDb(){
    error=true;
    errorField.setText("problems with the database");
  }
}
