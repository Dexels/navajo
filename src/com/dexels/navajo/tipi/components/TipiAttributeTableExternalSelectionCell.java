package com.dexels.navajo.tipi.components;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import com.dexels.navajo.tipi.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiAttributeTableExternalSelectionCell extends JPanel {
  JTextField valueField = new JTextField();
  JButton selectButton = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  ArrayList myListeners = new ArrayList();
  private String myType;

  public TipiAttributeTableExternalSelectionCell() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(gridBagLayout1);
    selectButton.setMaximumSize(new Dimension(21, 21));
    selectButton.setMargin(new Insets(0, 0, 0, 0));
    selectButton.setText("..");
    selectButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        selectButton_actionPerformed(e);
      }
    });
    valueField.setText("");
    this.add(selectButton,     new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 2, 2));
    this.add(valueField,           new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
  }

  public String toString(){
    return valueField.getText();
  }

  public void setText(String text){
    valueField.setText(text);
  }

  public void addActionListener(ActionListener listener){
    myListeners.add(listener);
  }

  public void setType(String type){
    myType = type;
  }

  public String getType(){
    return myType;
  }

  void selectButton_actionPerformed(ActionEvent e) {
    for(int i=0;i<myListeners.size();i++){
      ActionListener current = (ActionListener)myListeners.get(i);
      current.actionPerformed(e);
    }
  }

  public void setAttribute(TipiComponent t, String attr) {

  }
}
