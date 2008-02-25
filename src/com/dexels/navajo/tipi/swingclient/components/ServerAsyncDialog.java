package com.dexels.navajo.tipi.swingclient.components;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.swingclient.*;

import java.beans.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public class ServerAsyncDialog extends StandardWindow implements ServerAsyncListener {
  JPanel dialogPanel = new JPanel();
  JProgressBar progressBar = new JProgressBar();
  JButton cancelButton = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  private String myId = null;
  public ServerAsyncDialog() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
//    setB
    setButtonBarVisible(false);
    super.getContentPane().add(dialogPanel,BorderLayout.CENTER);
  }

  private final void jbInit() throws Exception {
    dialogPanel.setLayout(gridBagLayout1);
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelButton_actionPerformed(e);
      }
    });
    dialogPanel.add(progressBar,        new GridBagConstraints(0, 0, 1, 1, 1.0, 0.5
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(4, 4, 4, 4), 0, 0));
    dialogPanel.add(cancelButton,     new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  }
  public void serviceStarted(String id) {
    myId = id;
//    SwingClient
//    showWindow();
   SwingClient.getUserInterface().showCenteredWindow(this);
  }
  public void setProgress(String id, int d) {
    progressBar.setValue(d);

  }
  public void receiveServerAsync(Navajo n, String method, String serverId, String clientId) {
    try {
      closeWindow();
    }
    catch (PropertyVetoException ex) {
    }
    SwingClient.getUserInterface().showInfoDialog("I am finished!\nNavajo printed to stderr!");
//    System.err.println("\n\n==========================START ==========");
    //try {
      //n.write(System.err);
    //}
    //catch (NavajoException ex) {
    //  ex.printStackTrace();
    //}
//    System.err.println("\n\n==========================END ============");
  }

  public void save(){

  }

  public void handleException(Exception e) {
    try {
      closeWindow();
    }
    catch (PropertyVetoException ex) {
    }
    SwingClient.getUserInterface().showInfoDialog("Something went wrong!");
  }

  void cancelButton_actionPerformed(ActionEvent e) {
  try {
    NavajoClientFactory.getClient().killServerAsyncSend(myId);
  }
  catch (ClientException ex) {
    ex.printStackTrace();
  }
  }
}
