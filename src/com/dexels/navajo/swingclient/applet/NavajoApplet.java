package com.dexels.navajo.swingclient.applet;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import com.dexels.navajo.swingclient.*;
import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;
//import com.dexels.navajo.document.nanoimpl.*;

public class NavajoApplet extends JApplet {
  boolean isStandalone = false;
  String hostUrl;
  String username;
  String password;
  NavajoPanel navajoPanel = new NavajoPanel();
  ClientInterface myClient = NavajoClientFactory.getClient();

  //Get a parameter value
  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  //Construct the applet
  public NavajoApplet() {
  }
  //Initialize the applet
  public void init() {
    try {
      hostUrl = this.getParameter("hostUrl", "dexels.durgerlan.nl/sport-tester/servlet/Postman");
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    try {
      username = this.getParameter("username", "ROOT");
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    try {
      password = this.getParameter("password", "");
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    try {
      jbInit();
      navajoPanel.init(hostUrl,username,password);
//      Navajo mn = myClient.doSimpleSend(new Navajo(),"navajo_logon");
      Navajo mn = myClient.doSimpleSend(NavajoFactory.getInstance().createNavajo(),"InitUpdateMember");
/** @todo BEWARE */
//      navajoPanel.setNavajo(mn);

    }
    catch(Exception e) {
      e.printStackTrace();
    }



  }
  //Component initialization
  private void jbInit() throws Exception {
    this.setSize(new Dimension(640,480));
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(navajoPanel, BorderLayout.CENTER);
  }
  //Get Applet information
  public String getAppletInfo() {
    return "Applet Information";
  }
  //Get parameter info
  public String[][] getParameterInfo() {
    String[][] pinfo =
      {
      {"hostUrl", "String", "The server url"},
      {"username", "String", ""},
      {"password", "String", ""},
      };
    return pinfo;
  }

  //static initializer for setting look & feel
  static {
    try {
      //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch(Exception e) {
    }
  }
}