package com.dexels.navajo.tipi.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiExportFilterPanel extends JPanel {
  JPanel jPanel1 = new JPanel();
  TitledBorder titledBorder1;
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  ButtonGroup bg = new ButtonGroup();
  private String separator = ", ";
  JLabel filterOn = new JLabel();
  JLabel filterType = new JLabel();
  JLabel filterValue = new JLabel();
  JComboBox filterOnBox = new JComboBox();
  JComboBox filterTypeBox = new JComboBox();
  JTextField filterValueField = new JTextField();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public TipiExportFilterPanel() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(171, 171, 171)),"Filter");
    this.setLayout(gridBagLayout2);
    jPanel1.setBorder(titledBorder1);
    jPanel1.setDebugGraphicsOptions(0);
    jPanel1.setMaximumSize(new Dimension(32767, 32767));
    jPanel1.setLayout(gridBagLayout1);
    filterOn.setText("Filter op");
    filterType.setText("Filter type");
    filterValue.setText("Filter waarde");
    filterValueField.setText("");
    filterValueField.setHorizontalAlignment(SwingConstants.TRAILING);
    this.add(jPanel1,    new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 184, 74));
    jPanel1.add(filterOn,     new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    jPanel1.add(filterType,     new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    jPanel1.add(filterValue,     new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    jPanel1.add(filterOnBox,   new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    jPanel1.add(filterTypeBox,   new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
    jPanel1.add(filterValueField,   new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
  }


  void cancelButton_actionPerformed(ActionEvent e) {
    this.hide();
  }




}