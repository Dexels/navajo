package com.dexels.navajo.swingclient;

//import com.dexels.sportlink.client.swing.dialogs.*;
import javax.swing.*;
import com.dexels.navajo.document.*;
//import com.dexels.sportlink.client.swing.*;
import com.dexels.navajo.nanoclient.*;
import com.dexels.navajo.document.nanoimpl.*;
import com.dexels.navajo.client.*;

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
    Navajo m = null;
    try {
      m = NavajoClientFactory.getClient().doSimpleSend(NavajoFactory.getInstance().createNavajo(), msgName);
    }
    catch (ClientException ex) {
      ex.printStackTrace();
    }
    NavajoPanel np = new NavajoPanel(m);
    getContentPane().add(np);
    setVisible(true);
  }
}