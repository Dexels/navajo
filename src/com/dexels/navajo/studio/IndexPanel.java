package com.dexels.navajo.studio;

import java.awt.*;
import com.borland.jbcl.layout.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Title:        Navajo
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Dexels
 * @author Arjen Schoneveld en Martin Bergman
 * @version 1.0
 */

public class IndexPanel extends BaseStudioPanel {
  JButton formButton = new JButton();
  JButton scriptButton = new JButton();
  private Image tmlImage = Toolkit.getDefaultToolkit().getImage("tmlboom.jpg");
  private Image tslImage = Toolkit.getDefaultToolkit().getImage("tslboom.jpg");
  JLabel jLabel1 = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();


  public IndexPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public IndexPanel(RootStudioPanel rootPanel1) {
    try {
      rootPanel = rootPanel1;
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
//    title="Navajo Studio 0.7";
    formButton.setText("Form Editor");
    formButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        formButton_actionPerformed(e);
      }
    });
    this.setLayout(gridBagLayout1);
    scriptButton.setText("Script Editor");
    scriptButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        scriptButton_actionPerformed(e);
      }
    });
//    xYLayout1.setWidth(518);
//    xYLayout1.setHeight(369);
    scriptButton.setFocusPainted(false);
    formButton.setFocusPainted(false);
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 54));
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
    jLabel1.setText("Navajo Studio 1.0");
    this.setBackground(Color.yellow);
    this.add(scriptButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 100, 150));
    this.add(jLabel1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 50, 0, 50), 0, 0));
    this.add(formButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(0, 36, 0, 34), 100, 150));

  }

  void formButton_actionPerformed(ActionEvent e) {
    BPFLPanel formPanel =new BPFLPanel(rootPanel);
//    rootPanel.changeContentPane(formPanel);
  }

  void scriptButton_actionPerformed(ActionEvent e) {
    BPCLPanel scriptPanel =new BPCLPanel(rootPanel);
//    rootPanel.changeContentPane(scriptPanel);
  }

}