package com.dexels.navajo.beheer;

import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import java.awt.event.*;
import com.dexels.navajo.server.ConditionData;
import javax.swing.border.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class ConditionRowPanel extends BaseNavajoPanel {
  JTextField serviceNameField = new JTextField();
  JTextField conditionField = new JTextField();
  JButton EditButton = new JButton();

  ConditionData cd = new ConditionData();
  String serviceName="";
  String userName="";
  int userId=-1;
  TitledBorder titledBorder1;
  GridLayout gridLayout1 = new GridLayout();


  public ConditionRowPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public ConditionRowPanel(ConditionData cd1, RootPanel frame, String UserName1, int userId1) {
    rootPanel = frame;
    cd = cd1;
    userName=UserName1;
    userId=userId1;
    try{
      serviceName = rootPanel.auth.simpleSelect(rootPanel.access, "name", "services", "id", Integer.toString(cd.serviceId));
    }
    catch(Exception ex){
      ex.printStackTrace(System.out);
    }

    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    titledBorder1 = new TitledBorder("");
    serviceNameField.setBackground(SystemColor.window);
    serviceNameField.setBorder(null);
    serviceNameField.setEditable(false);
    serviceNameField.setText(serviceName);
    conditionField.setBackground(SystemColor.window);
    conditionField.setBorder(null);
    conditionField.setEditable(false);
    conditionField.setText(cd.condition);

    this.setLayout(gridLayout1);

    EditButton.setText("Edit");
    EditButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        EditButton_actionPerformed(e);
      }
    });
    this.setBackground(Color.white);
    this.setMinimumSize(new Dimension(455, 30));
    this.add(serviceNameField, null);
    this.add(conditionField, null);
    this.add(EditButton, null);
  }

  void EditButton_actionPerformed(ActionEvent e) {
    ConditionPanel cp = new ConditionPanel(cd, rootPanel, serviceName, userName, userId);
    rootPanel.changeContentPane(cp);
  }
}