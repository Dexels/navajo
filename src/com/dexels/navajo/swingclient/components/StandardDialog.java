package com.dexels.navajo.swingclient.components;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
//import com.dexels.sportlink.client.swing.*;
//import com.dexels.sportlink.client.swing.components.*;
import java.util.*;

import com.dexels.navajo.swingclient.*;
//import com.dexels.sportlink.client.swing.dialogs.*;
/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class StandardDialog extends BaseDialog implements DialogConstants {

  private boolean doWindowClose = true;

  ResourceBundle res;
  private boolean isCommitted = false;
  BorderLayout borderLayout1 = new BorderLayout();
  protected IconButtonPanel iconButtonPanel = new IconButtonPanel();
  JToolBar dialogToolbar = new JToolBar();

  public StandardDialog() {
    dialogToolbar.setFloatable(false);
    try {
      res = SwingClient.getUserInterface().getResource("com.dexels.sportlink.client.swing.dialogs.StandardDialog");
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void commit(){
  }

  public void discard(){
  }

  public void addMainPanel(BasePanel p) {
    this.getContentPane().add(p,BorderLayout.CENTER);
    mainPanel = p;
  }


  public void setDoWindowClose(boolean state){
    doWindowClose = state;
  }

  public void setMode(int mode) {
    iconButtonPanel.setAllVisible(false);
    switch (mode) {
      case MODE_OK_CANCEL:
        iconButtonPanel.setButtonVisible(iconButtonPanel.OK_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.CANCEL_BUTTON,true);
        break;
      case MODE_CONFIRM:
      case MODE_CLOSE:
        iconButtonPanel.setButtonVisible(iconButtonPanel.OK_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.CANCEL_BUTTON,false);
        break;
      case MODE_OK_CANCEL_APPLY:
        iconButtonPanel.setButtonVisible(iconButtonPanel.SAVE_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.OK_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.CANCEL_BUTTON,true);
        break;
      case MODE_OK_CANCEL_APPLY_INSERT:
        iconButtonPanel.setButtonVisible(iconButtonPanel.SAVE_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.OK_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.CANCEL_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.INSERT_BUTTON,true);
        break;
      case MODE_OK_CANCEL_INSERT_DELETE:
        iconButtonPanel.setButtonVisible(iconButtonPanel.DELETE_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.OK_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.CANCEL_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.INSERT_BUTTON,true);
        break;
      case MODE_OK_CANCEL_APPLY_INSERT_DELETE:
        iconButtonPanel.setButtonVisible(iconButtonPanel.SAVE_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.DELETE_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.OK_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.CANCEL_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.INSERT_BUTTON,true);
        break;
    }
//
  }


  public boolean isCommitted() {
    return isCommitted;
  }

  public void setCommitted(boolean state){
    isCommitted = state;
  }

  private void jbInit() throws Exception {
    dialogToolbar.setFloatable(false);
    this.getContentPane().setLayout(borderLayout1);
    setModal(true);
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    iconButtonPanel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iconButtonPanel_actionPerformed(e);
      }
    });
    dialogToolbar.add(iconButtonPanel, null);
    this.getContentPane().add(dialogToolbar,  BorderLayout.SOUTH);
  }

  void this_windowClosing(WindowEvent e) {
    if (!isCommitted()) {
      discard();
    }
    setVisible(false);
  }
  public void delete() {
  }

  public void newItem() {
  }

  public void insert() {
  }

  void iconButtonPanel_actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals(IconButtonPanel.OK_BUTTON)) {
      if (!isCommitted()) {
        isCommitted = true;
        commit();
      }
      if(doWindowClose){
        closeWindow();
      }
      setDoWindowClose(true);
      return;
    }
    if (e.getActionCommand().equals(IconButtonPanel.CANCEL_BUTTON)) {
      if (!isCommitted()) {
        discard();
      }
      if(doWindowClose){
        closeWindow();
      }
      setDoWindowClose(true);
      return;
    }
    if (e.getActionCommand().equals(IconButtonPanel.DELETE_BUTTON)) {
      delete();
      return;
    }
    if (e.getActionCommand().equals(IconButtonPanel.NEW_BUTTON)) {
      newItem();
      return;
    }
    if (e.getActionCommand().equals(IconButtonPanel.INSERT_BUTTON)) {
      insert();
      return;
    }
    if (e.getActionCommand().equals(IconButtonPanel.SAVE_BUTTON)) {
      isCommitted = true;
      commit();
      return;
    }

  }
  public void setVisible(boolean parm1) {
    super.setVisible( parm1);
//    iconButtonPanel.requestDefaultFocus();
    iconButtonPanel.requestFocus();
  }
  public void showDialog() {
      SwingClient.getUserInterface().addDialog(this);
  }
}
