package com.dexels.navajo.swingclient.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
//import com.dexels.sportlink.client.swing.*;
import com.dexels.navajo.swingclient.*;

/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class IconButtonPanel extends JPanel {
  ResourceBundle res;
  JButton newButton = new JButton();
  JButton saveButton = new JButton();
  JButton deleteButton = new JButton();
  JButton insertButton = new JButton();
  JButton cancelButton = new JButton();
  FlowLayout flowLayout1 = new FlowLayout();
  ArrayList listeners = new ArrayList();
  public final static String NEW_BUTTON = "NEW";
  public final static String SAVE_BUTTON = "SAVE";
  public final static String DELETE_BUTTON = "DELETE";
  public final static String INSERT_BUTTON = "INSERT";
  public final static String CANCEL_BUTTON = "CANCEL";
  public final static String OK_BUTTON = "OK";
  JButton okButton = new JButton();

  public IconButtonPanel() {
    try {
      res = SwingClient.getUserInterface().getResource("com.dexels.sportlink.client.swing.components");
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    setAllVisible(false);
//    okButton.requestDefaultFocus();
//    FocusTraversalPolicy.getDefaultComponent().requestFocus();
  }
  private void jbInit() throws Exception {
    newButton.setToolTipText(res.getString("IconButtonPanel_newButton"));
    newButton.setActionCommand(NEW_BUTTON);
    newButton.setIcon(new ImageIcon(UserInterface.class.getResource("images/new.gif")));
    newButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newButton_actionPerformed(e);
      }
    });
    saveButton.setToolTipText(res.getString("IconButtonPanel_saveButton"));
    saveButton.setActionCommand(SAVE_BUTTON);
    saveButton.setIcon(new ImageIcon(UserInterface.class.getResource("images/save.gif")));
    saveButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveButton_actionPerformed(e);
      }
    });
    deleteButton.setToolTipText(res.getString("IconButtonPanel_deleteButton"));
    deleteButton.setActionCommand(DELETE_BUTTON);
    deleteButton.setIcon(new ImageIcon(UserInterface.class.getResource("images/delete.gif")));
    deleteButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteButton_actionPerformed(e);
      }
    });
    insertButton.setToolTipText(res.getString("IconButtonPanel_insertButton"));
    insertButton.setActionCommand(INSERT_BUTTON);
    insertButton.setIcon(new ImageIcon(UserInterface.class.getResource("images/insert.gif")));
    insertButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        insertButton_actionPerformed(e);
      }
    });
    cancelButton.setToolTipText(res.getString("IconButtonPanel_cancelButton"));
    cancelButton.setActionCommand(CANCEL_BUTTON);
    cancelButton.setIcon(new ImageIcon(UserInterface.class.getResource("images/cancel.gif")));
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelButton_actionPerformed(e);
      }
    });
    okButton.setToolTipText(res.getString("IconButtonPanel_okButton"));
    okButton.setIcon(new ImageIcon(UserInterface.class.getResource("images/ok.gif")));
    okButton.setActionCommand(OK_BUTTON);
    okButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okButton_actionPerformed(e);
      }
    });
    this.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    this.add(newButton, null);
    this.add(saveButton, null);
    this.add(deleteButton, null);
    this.add(insertButton, null);
    this.add(okButton, null);
    this.add(cancelButton, null);
  }

  public void setButtonVisible(String button, boolean state){
    if(button.equals(NEW_BUTTON)) {
      newButton.setVisible(state);
    }
    if(button.equals(SAVE_BUTTON)) {
      saveButton.setVisible(state);
    }
    if(button.equals(INSERT_BUTTON)) {
      insertButton.setVisible(state);
    }
    if(button.equals(DELETE_BUTTON)) {
      deleteButton.setVisible(state);
    }
    if(button.equals(CANCEL_BUTTON)) {
      cancelButton.setVisible(state);
    }
    if(button.equals(OK_BUTTON)) {
      okButton.setVisible(state);
    }
  }

  public void setAllVisible(boolean b) {
    newButton.setVisible(b);
    saveButton.setVisible(b);
    insertButton.setVisible(b);
    deleteButton.setVisible(b);
    cancelButton.setVisible(b);
    okButton.setVisible(b);
  }


  public void addActionListener(java.awt.event.ActionListener al){
    listeners.add(al);
  }

  public void buttonPressed(ActionEvent e){
    for(int i=0;i<listeners.size();i++){
      java.awt.event.ActionListener current = (java.awt.event.ActionListener)listeners.get(i);
      current.actionPerformed(e);
    }
  }

  void newButton_actionPerformed(ActionEvent e) {
    buttonPressed(e);
  }

  void saveButton_actionPerformed(ActionEvent e) {
    buttonPressed(e);
  }

  void deleteButton_actionPerformed(ActionEvent e) {
    buttonPressed(e);
  }

  void insertButton_actionPerformed(ActionEvent e) {
    buttonPressed(e);
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    buttonPressed(e);
  }

  void okButton_actionPerformed(ActionEvent e) {
    buttonPressed(e);
  }
  public void requestFocus() {
//    /**@todo: Override this javax.swing.JComponent method*/
//    super.requestFocus();
    okButton.requestFocus();
  }
}
