package com.dexels.navajo.beheer;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import java.util.Vector;
import com.dexels.navajo.util.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class ServiceGroupPanel extends BaseNavajoPanel {
  private   JTextField NameField = new JTextField();
  private   JLabel jLabel1 = new JLabel();
  private   JLabel jLabel2 = new JLabel();

  private   int handlerChoice = 2;
  private   String groupName="";
  private   String oldGroupName="";
  private   String servlet="";
  private   int id = -1;
  private   JRadioButton GenericHandlerButton = new JRadioButton();
  private   JRadioButton OtherHandlerButton = new JRadioButton();
  private   JTextField OtherHandlerField = new JTextField();
  private   JButton BrowseButton = new JButton();
  private   ButtonGroup buttonGroup1 = new ButtonGroup();

  private   JPanel centerPanel = new JPanel();
  private   BorderLayout borderLayout1 = new BorderLayout();
  private   GridBagLayout gridBagLayout1 = new GridBagLayout();

  public ServiceGroupPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public ServiceGroupPanel(RootPanel frame, String groupName1, int id1) {
    rootPanel = frame;
    id = id1;

    if(groupName!=null){
      if(id==-1)
        newEntry=true;
      else{
        groupName = groupName1;
        oldGroupName =groupName;
      }
    }
    else{
      newEntry=true;
    }

    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    title="Service Group";
    NameField.setText(groupName);
    jLabel1.setText("Group Name");
    jLabel2.setText("Service Handler");

    //servletsBox.addItem("MaintainanceServlet");

    servlet=rootPanel.auth.simpleSelect(rootPanel.access, "servlet", "service_group", "id", Integer.toString(id));


    GenericHandlerButton.setText("GenericHandler");
    GenericHandlerButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        GenericHandlerButton_actionPerformed(e);
      }
    });
    BrowseButton.setToolTipText("");
    BrowseButton.setText("Browse");
    OtherHandlerButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        OtherHandlerButton_actionPerformed(e);
      }
    });

    centerPanel.setLayout(gridBagLayout1);
    centerPanel.setBackground(Color.yellow);
    buttonGroup1.add(OtherHandlerButton);
    buttonGroup1.add(GenericHandlerButton);

    if (servlet.equals("com.dexels.navajo.server.GenericHandler")) {
      GenericHandlerButton.setSelected(true);
      OtherHandlerButton.setSelected(false);
    }
    else {
      GenericHandlerButton.setSelected(false);
      OtherHandlerButton.setSelected(true);
      OtherHandlerField.setText(servlet);
    }

    centerPanel.add(NameField, new GridBagConstraints(1, 0, 2, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(67, 13, 0, 66), 163, -3));
    centerPanel.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(67, 23, 0, 0), 41, 3));
    centerPanel.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 23, 7, 0), 26, 2));
    centerPanel.add(GenericHandlerButton, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(11, 13, 0, 122), -1, 0));
    centerPanel.add(OtherHandlerField, new GridBagConstraints(2, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 157, 0), 210, 0));
    centerPanel.add(OtherHandlerButton, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 13, 157, 0), 0, 0));
    centerPanel.add(BrowseButton, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 6, 157, 36), 14, -5));
    this.setLayout(borderLayout1);
    this.add(centerPanel, BorderLayout.CENTER);
    applyTemplate2();
  }

  void deleteButton_actionPerformed(ActionEvent e) {
    if(id==0){
      errorField.setText("Cannot delete(This is reservered for the unassigned services)");
    }
    else{
      try{
        rootPanel.auth.deleteServiceGroup(rootPanel.access, groupName);
        rootPanel.changeToBeheerPanel();
      }
      catch(Exception ex){
        ex.printStackTrace(System.out);
        errorWithDb();
      }
    }
  }

  void okButton_actionPerformed(ActionEvent e) {
    groupName = NameField.getText();

    System.out.println("handlerChoice = " + handlerChoice);

    if (handlerChoice == 1)
      servlet = "com.dexels.navajo.server.GenericHandler";
    else if (handlerChoice == 2)
      servlet = this.OtherHandlerField.getText();

    System.out.println("Handler: " + servlet);

    if(groupName.equals("")){
      error=true;
      errorField.setText("Please fill group name");
    }
    else{
      try{
        Vector[] groupNames=rootPanel.auth.select(rootPanel.access, "select name from service_group", 1);
        for(int i = 0; i<groupNames[0].size(); i++){
          if(groupName.equals((String)groupNames[0].get(i))&&!groupName.equals(oldGroupName)){
            error=true;
            errorField.setText("Group name allready exist");
          }
        }
      }
      catch(Exception ex){
        ex.printStackTrace(System.out);
        errorWithDb();
      }
      if(!error){
        if(newEntry){
          try{
            rootPanel.auth.addServiceGroup(rootPanel.access, groupName, servlet);
          }
          catch(Exception ex){
            ex.printStackTrace(System.out);
          }
        }
        else{
          try{
            rootPanel.auth.update(rootPanel.access, "service_group", new String[]{"name", "servlet"},
            new String[]{groupName, servlet}, new String[]{"id"}, new String[]{Integer.toString(id)});
          }
          catch(Exception ex){
            ex.printStackTrace(System.out);
          }
        }
      }
      if(!error){
        rootPanel.changeToBeheerPanel();
      }
    }
    error=false;
    groupName=oldGroupName;
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    rootPanel.changeToBeheerPanel();
  }

  private   void GenericHandlerButton_actionPerformed(ActionEvent e) {
      System.out.println("in GenericHandlerButton_actionPerformed()");
      handlerChoice = 1;
  }

  private   void OtherHandlerButton_actionPerformed(ActionEvent e) {
      System.out.println("in OtherHandlerButton_actionPerformed()");
      handlerChoice = 2;
  }

}