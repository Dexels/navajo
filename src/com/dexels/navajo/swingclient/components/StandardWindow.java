package com.dexels.navajo.swingclient.components;

/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
//import com.dexels.sportlink.client.swing.*;
//import com.dexels.sportlink.client.swing.components.*;
import java.util.*;

import com.dexels.navajo.swingclient.*;
//import com.dexels.sportlink.client.swing.dialogs.*;

public class StandardWindow extends BaseWindow  implements DialogConstants {
  private ResourceBundle res;
  protected JToolBar dialogToolbar = new JToolBar();
  protected BaseButton closeButton = new BaseButton();
  protected BaseButton insertButton = new BaseButton();
  protected BaseButton saveButton = new BaseButton();
  protected BaseButton clearButton = new BaseButton();
  private BasePanel myPanel = null;

  public StandardWindow() {
    try {
      res = SwingClient.getUserInterface().getResource("com.dexels.sportlink.client.swing.dialogs");
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  protected void setButtonBarVisible(boolean b) {
    dialogToolbar.setVisible(b);
  }

  public void addButtonToBar(BaseButton but){
    dialogToolbar.add(but);
  }

  public void setToolbarLocation(int location){
    dialogToolbar.setOrientation(location);
  }
  public void addMainPanel(BasePanel p) {
    if(myPanel != null){
      mainPanel.remove(myPanel);
    }
    myPanel = p;
    mainPanel.add(myPanel,BorderLayout.CENTER);

  }

  private void jbInit() throws Exception {
    closeButton.setText(res.getString("StandardWindow_close"));
    closeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        closeButton_actionPerformed(e);
      }
    });
    insertButton.setText(res.getString("StandardWindow_insert"));
    insertButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        insertButton_actionPerformed(e);
      }
    });
    saveButton.setText(res.getString("StandardWindow_save"));
    saveButton.setEnabled(false);
    saveButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveButton_actionPerformed(e);
      }
    });
    clearButton.setText(res.getString("StandardWindow_clear"));
    clearButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        clearButton_actionPerformed(e);
      }
    });
    this.getContentPane().add(dialogToolbar,  BorderLayout.SOUTH);
    dialogToolbar.add(Box.createGlue());
    dialogToolbar.add(insertButton, null);
    dialogToolbar.add(saveButton, null);
    dialogToolbar.add(clearButton, null);
    dialogToolbar.add(closeButton, null);
  }

  void clearButton_actionPerformed(ActionEvent e) {
    clear();
  }

  void saveButton_actionPerformed(ActionEvent e) {
    save();
  }

  void closeButton_actionPerformed(ActionEvent e) {
    super.closeWindow();
//    windowHasClosed();
  }

  void insertButton_actionPerformed(ActionEvent e) {
    insert();
  }

  public void clear() {
    insertButton.setEnabled(true);
    saveButton.setEnabled(false);
  }

  public void save() {

  }

  public void insert() {

  }

  public void setLoaded() {
    insertButton.setEnabled(false);
    saveButton.setEnabled(true);

  }

}