package com.dexels.navajo.beheer;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import com.borland.jbcl.layout.*;
import com.dexels.navajo.server.ServiceGroup;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Albert Lo
 * @version 1.0
 */

public class ServiceGroupRowPanel extends BaseNavajoPanel {
  private   JTextField nameField = new JTextField();
  private   ServiceGroup sg = new ServiceGroup(1,"groupname","a servlet");
  private   String userName = "";
  private   JButton removeButton = new JButton();
  private   GridBagLayout gridBagLayout1 = new GridBagLayout();

  public ServiceGroupRowPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public ServiceGroupRowPanel(ServiceGroup sg1, String userName1, RootPanel frame) {

    sg = sg1;
    userName = userName1;
    rootPanel=frame;

    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    nameField.setBackground(SystemColor.window);
    nameField.setBorder(null);
    nameField.setDisabledTextColor(Color.lightGray);
    nameField.setEditable(false);
    nameField.setText(sg.name);

    this.setLayout(gridBagLayout1);
    removeButton.setText("Remove");
    removeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        removeButton_actionPerformed(e);
      }
    });
    this.setBackground(Color.white);
    this.setMinimumSize(new Dimension(455, 30));
    this.setPreferredSize(new Dimension(455, 30));
    this.add(nameField, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(1, 18, 3, 0), 355, 0));
    this.add(removeButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(1, 9, 3, 0), 0, -1));
  }

  private   void removeButton_actionPerformed(ActionEvent e) {
    try{
      rootPanel.auth.deleteAuthorisation(rootPanel.access, userName, sg.id);
    }
    catch(Exception ex){
      ex.printStackTrace(System.out);
    }
//    UserPanel userPanel = new UserPanel(userName, rootPanel, userPanel.AUTHORISE_TAB);
    UserPanel userPanel = new UserPanel(userName, rootPanel, 0);
    rootPanel.changeContentPane(userPanel);
  }


}