package com.dexels.navajo.swingclient;

//import com.dexels.sportlink.client.swing.dialogs.*;
import javax.swing.*;
import com.dexels.navajo.nanodocument.*;
//import com.dexels.sportlink.client.swing.*;
import com.dexels.navajo.nanoclient.*;

public class GenericDialog extends JDialog {

  private static GenericDialog instance = null;

  public static GenericDialog getInstance(){
    if(instance == null){
      instance = new GenericDialog();
    }
    return instance;
  }

  public GenericDialog() {
    super(SwingClient.getUserInterface().getMainFrame());
    String msgName = JOptionPane.showInputDialog("Enter init message e.g. InitUpdateMember:");
    Navajo m = AdvancedNavajoClient.doSimpleSend(msgName);
    NavajoPanel np = new NavajoPanel(m);
    getContentPane().add(np);
    setVisible(true);
  }
}