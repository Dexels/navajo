package com.dexels.navajo.tipi.components;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiExportSeparatorPanel extends JPanel {
  JPanel jPanel1 = new JPanel();
  TitledBorder titledBorder1;
  JRadioButton commaOption = new JRadioButton();
  JRadioButton tabOption = new JRadioButton();
  JRadioButton semicolonOption = new JRadioButton();
  JRadioButton spaceOption = new JRadioButton();
  JRadioButton otherOption = new JRadioButton();
  JTextField customSeparatorField = new JTextField();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  ButtonGroup bg = new ButtonGroup();
  JCheckBox includeTitles = new JCheckBox();
  public TipiExportSeparatorPanel() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout2);
    jPanel1.setBorder(titledBorder1);
    jPanel1.setLayout(gridBagLayout1);
    jPanel1.add(commaOption, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    jPanel1.add(tabOption, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    jPanel1.add(semicolonOption, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    jPanel1.add(spaceOption, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    jPanel1.add(otherOption, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
    jPanel1.add(customSeparatorField, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 20, 0));
    jPanel1.add(includeTitles, new GridBagConstraints(0, 5, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(171, 171, 171)),"Scheidings teken");
    commaOption.setSelected(true);
    commaOption.setText("Komma");
    tabOption.setText("Tab");
    semicolonOption.setText("Puntkomma");
    spaceOption.setText("Spatie");
    otherOption.setText("Anders nl.");
    customSeparatorField.setText("");
    includeTitles.setSelected(true);
    includeTitles.setText("Kolomnamen op de eerste rij");
    bg.add(commaOption);
    bg.add(tabOption);
    bg.add(semicolonOption);
    bg.add(spaceOption);
    bg.add(otherOption);
    this.add(jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.SOUTHEAST, GridBagConstraints.BOTH, new Insets(4, 4, 0, 0), 0, 0));
  }

  public String getSelectedSeparator(){
    if(commaOption.isSelected()){
      return ",";
    }else if(tabOption.isSelected()){
      return "\t";
    }else if(semicolonOption.isSelected()){
      return ";";
    }else if(spaceOption.isSelected()){
      return " ";
    }else if(otherOption.isSelected()){
      return customSeparatorField.getText();
    }else{
      return ",";
    }
  }
  public boolean isHeaderSelected() {
    return includeTitles.isSelected();
  }
}